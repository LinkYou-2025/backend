package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.GeneralException;
import com.umc.linkyou.awsS3.AwsS3Service;
import com.umc.linkyou.converter.AwsS3Converter;
import com.umc.linkyou.converter.LinkuConverter;
import com.umc.linkyou.domain.*;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.classification.Domain;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.googleImgParser.LinkToImageService;
import com.umc.linkyou.repository.*;
import com.umc.linkyou.repository.LinkuRepository.LinkuRepository;
import com.umc.linkyou.repository.classification.CategoryRepository;
import com.umc.linkyou.repository.classification.DomainRepository;
import com.umc.linkyou.repository.mapping.LinkuFolderRepository;
import com.umc.linkyou.repository.mapping.UsersLinkuRepository;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkuServiceImpl implements LinkuService {

    private final LinkuRepository linkuRepository;
    private final CategoryRepository categoryRepository;
    private final EmotionRepository emotionRepository;
    private final DomainRepository domainRepository;
    private final LinkuFolderRepository linkuFolderRepository;
    private final UsersLinkuRepository usersLinkuRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;
    private final LinkToImageService linkToImageService;
    private final RecentViewedLinkuRepository recentViewedLinkuRepository;

    @Override
    @Transactional
    public LinkuResponseDTO.LinkuResultDTO createLinku(Long userId, LinkuRequestDTO.LinkuCreateDTO dto, MultipartFile image) {
        Category category = categoryRepository.findById(16L)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CATEGORY_NOT_FOUND));

        Emotion emotion = (dto.getEmotionId() == null || dto.getEmotionId() <= 0)
                ? emotionRepository.findById(2L)
                .orElseThrow(() -> new GeneralException(ErrorStatus._EMOTION_NOT_FOUND))
                : emotionRepository.findById(dto.getEmotionId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._EMOTION_NOT_FOUND));

        String domainTail = extractDomainTail(dto.getLinku());
        //도메인 가져오기
        Domain domain = (domainTail != null)
                ? domainRepository.findByDomainTail(domainTail)
                .orElseGet(() -> domainRepository.findById(1L)
                        .orElseThrow(() -> new GeneralException(ErrorStatus._DOMAIN_NOT_FOUND)))
                : domainRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus._DOMAIN_NOT_FOUND));
        //중분료 폴더 가져오기
        Folder folder = folderRepository.findById(16L)
                .orElseThrow(() -> new GeneralException(ErrorStatus._FOLDER_NOT_FOUND));

        //새로운 링크 생성하기
        Linku linku = LinkuConverter.toLinku(dto.getLinku(), category, domain);
        linkuRepository.save(linku);

        //요청 보낸 사용자 저장
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));

        //image저장
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = AwsS3Converter.toImageUrl(image, awsS3Service);
        } else {
            // 링크로 대표 이미지 추출 저장 실패 시 null로 저장
            imageUrl = linkToImageService.getRelatedImageFromUrl(dto.getLinku());
        }

        //usersLinku생성
        UsersLinku usersLinku = LinkuConverter.toUsersLinku(user, linku, emotion, dto.getMemo(),imageUrl);
        usersLinkuRepository.save(usersLinku);

        LinkuFolder linkuFolder = LinkuConverter.toLinkuFolder(folder, usersLinku);
        linkuFolderRepository.save(linkuFolder);

        return LinkuConverter.toLinkuResultDTO(
                userId, linku, usersLinku, linkuFolder, category, emotion, domain
        );
    }
// 링큐 생성

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<LinkuResponseDTO.LinkuIsExistDTO>> existLinku(Long userId, String url) {

        // 1. 영상 링크 차단
        if (isVideoLink(url)) {
            ErrorStatus error = ErrorStatus._LINKU_VIDEO_NOT_ALLOWED;
            return ResponseEntity.status(error.getHttpStatus())
                    .body(ApiResponse.onFailure(error.getCode(), error.getMessage(), null));
        }

        // 2. 유효하지 않은 링크 차단
        if (!isValidUrl(url)) {
            ErrorStatus error = ErrorStatus._LINKU_INVALID_URL;
            return ResponseEntity.status(error.getHttpStatus())
                    .body(ApiResponse.onFailure(error.getCode(), error.getMessage(), null));
        }

        // 3. 기존에 링크 저장 여부 확인
        Optional<UsersLinku> usersLinkuOpt = usersLinkuRepository.findByUserIdAndLinku_Linku(userId, url);

        if (usersLinkuOpt.isPresent()) {
            Linku linku = usersLinkuOpt.get().getLinku();
            LinkuResponseDTO.LinkuIsExistDTO dto = LinkuConverter.toLinkuIsExistDTO(userId, usersLinkuOpt.orElse(null));
            return ResponseEntity.ok(ApiResponse.onSuccess("링큐가 이미 존재합니다.", dto));
        } else {
            LinkuResponseDTO.LinkuIsExistDTO dto = LinkuConverter.toLinkuIsExistDTO(userId, null);
            return ResponseEntity.ok(ApiResponse.onSuccess("링큐가 존재하지 않습니다.", dto));
        }
    }
    //링크가 이미 존재하는 지 여부 판단



    // URL에서 도메인명만 추출 (예: https://blog.naver.com/abc → blog.naver.com)
    private static String extractDomainTail(String url) {
        try {
            java.net.URI uri = new java.net.URI(url);
            String domain = uri.getHost();
            if (domain != null && domain.startsWith("www.")) {
                domain = domain.substring(4);
            }
            return domain;
        } catch (Exception e) {
            return null;
        }
    }

    //
    private boolean isVideoLink(String url) {
        // 영상 플랫폼 도메인 리스트
        List<String> videoDomains = List.of(
                "youtube.com", "youtu.be", "vimeo.com", "tiktok.com", "dailymotion.com", "kakao.tv", "navertv", "tv.kakao.com"
        );

        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host == null) return false;

            return videoDomains.stream().anyMatch(host::contains);
        } catch (URISyntaxException e) {
            return false;
        }
    }
    private boolean isValidUrl(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD"); // 본문 없이 빠르게 존재 여부 확인
            connection.setConnectTimeout(3000); // 3초 제한
            connection.setReadTimeout(3000);

            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 400; // 2xx or 3xx는 유효
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<LinkuResponseDTO.LinkuResultDTO>> detailGetLinku(Long userId, Long linkuId) {
        // 1. 해당 사용자가 이 링크(linkuId)를 저장한 UsersLinku 찾기.
        UsersLinku usersLinku = usersLinkuRepository.findByUser_IdAndLinku_LinkuId(userId, linkuId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._USER_LINKU_NOT_FOUND));
        // 최근 열람 기록 upDate
        updateRecentViewedLinku(userId, linkuId);

        // 2. Linku는 UsersLinku에서 직접 꺼낼 수 있음
        Linku linku = usersLinku.getLinku();

        // 3. 기타 연관 엔티티 처리
        Category category = linku.getCategory();
        Emotion emotion = usersLinku.getEmotion();
        Domain domain = linku.getDomain();

        // 4. LinkuFolder 최신 1개 조회
        LinkuFolder linkuFolder =
                linkuFolderRepository.findFirstByUsersLinku_UserLinkuIdOrderByLinkuFolderIdDesc(usersLinku.getUserLinkuId()).orElse(null);

        // 5. DTO 변환 및 반환
        LinkuResponseDTO.LinkuResultDTO dto = LinkuConverter.toLinkuResultDTO(
                userId, linku, usersLinku, linkuFolder, category, emotion, domain
        );
        return ResponseEntity.ok(ApiResponse.onSuccess("링크 상세 조회 성공", dto));
    } //링크 상세조회


    @Transactional
    public void updateRecentViewedLinku(Long userId, Long linkuId) {
        // 이미 기록이 있으면 viewCount, viewedAt만 업데이트
        RecentViewedLinku rv = recentViewedLinkuRepository.findByUser_IdAndLinku_LinkuId(userId, linkuId)
                .orElse(null);
        if (rv != null) {
            rv.setViewedAt(LocalDateTime.now());
            rv.setViewCount(rv.getViewCount() + 1);
        } else {
            // 없으면 새로 insert
            rv = RecentViewedLinku.builder()
                    .user(userRepository.getReferenceById(userId))
                    .linku(linkuRepository.getReferenceById(linkuId))
                    .viewedAt(LocalDateTime.now())
                    .viewCount(1L)
                    .build();
        }
        recentViewedLinkuRepository.save(rv);
    }




}

