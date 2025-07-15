package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.GeneralException;
import com.umc.linkyou.awsS3.AwsS3Service;
import com.umc.linkyou.converter.AwsS3Converter;
import com.umc.linkyou.converter.LinkuConverter;
import com.umc.linkyou.domain.*;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.repository.*;
import com.umc.linkyou.repository.LinkuRepository.LinkuRepository;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
                .orElseGet(() -> domainRepository.findById(21L)
                        .orElseThrow(() -> new GeneralException(ErrorStatus._DOMAIN_NOT_FOUND)))
                : domainRepository.findById(21L)
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
        String imageUrl = AwsS3Converter.toImageUrl(image, awsS3Service);

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
        Optional<UsersLinku> usersLinkuOpt = usersLinkuRepository.findByUserIdAndLinku_Linku(userId, url);

        if (usersLinkuOpt.isPresent()) {
            // 이미 존재하는 경우
            Linku linku = usersLinkuOpt.get().getLinku();
            LinkuResponseDTO.LinkuIsExistDTO dto = LinkuConverter.toLinkuIsExistDTO(userId, usersLinkuOpt.orElse(null));
            return ResponseEntity.ok(ApiResponse.onSuccess("이미 존재합니다.", dto));
        } else {
            // 존재하지 않는 경우
            LinkuResponseDTO.LinkuIsExistDTO dto = LinkuConverter.toLinkuIsExistDTO(userId, null);
            return ResponseEntity.ok(ApiResponse.onSuccess("존재하지 않습니다.", dto));
        }
    } //링크가 이미 존재하는 지 여부 판단



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



}

