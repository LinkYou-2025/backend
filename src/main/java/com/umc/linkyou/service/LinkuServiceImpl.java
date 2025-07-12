package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.converter.LinkuConverter;
import com.umc.linkyou.domain.*;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.repository.*;
import com.umc.linkyou.repository.LinkuRepository.LinkuRepository;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public LinkuResponseDTO.LinkuResultDTO createLinku(Long userId, LinkuRequestDTO.LinkuCreateDTO dto) {
        // 1. 카테고리: 16번(기타)
        Category category = categoryRepository.findById(16L)
                .orElseThrow(() -> new IllegalArgumentException("기타 카테고리 없음"));

        // 2. 감정: 없으면 '평온'(id=2)
        Emotion emotion;
        if (dto.getEmotionId() == null || dto.getEmotionId() <= 0) {
            emotion = emotionRepository.findById(2L)
                    .orElseThrow(() -> new IllegalArgumentException("기본 감정(평온) 없음"));
        } else {
            emotion = emotionRepository.findById(dto.getEmotionId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 감정 없음"));
        }


        // 3. 도메인: linku에서 도메인명 추출 → 없으면 domain_id=21(기타)
        String domainTail = extractDomainTail(dto.getLinku());
        Domain domain = (domainTail != null)
                ? domainRepository.findByDomainTail(domainTail).orElseGet(() -> domainRepository.findById(21L).orElseThrow())
                : domainRepository.findById(21L).orElseThrow();

        // 4. 시딩한 기타 폴더 가져옴
        Folder folder = folderRepository.findById(16L)
                .orElseThrow(() -> new IllegalArgumentException("기타 폴더 없음"));

        // 5. Linku 엔티티 생성 (memo, imageUrl, emotionId 없음)
        Linku linku = Linku.builder()
                .category(category)
                .domain(domain)
                .linku(dto.getLinku())
                .build();
        linkuRepository.save(linku);

        // 6. UsersLinku 매핑 (memo, imageUrl, emotionId 추가)
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        UsersLinku usersLinku = UsersLinku.builder()
                .user(users)
                .linku(linku)
                .emotion(emotion)
                .memo(dto.getMemo())
                .imageUrl(null)
                .build();
        usersLinkuRepository.save(usersLinku);

        // 7. LinkuFolder 생성 (linkuId → userLinkuId로 변경)
        LinkuFolder linkuFolder = LinkuFolder.builder()
                .folder(folder)
                .usersLinku(usersLinku)
                .build();
        linkuFolderRepository.save(linkuFolder);

        // 8. DTO 변환 후 반환 (LinkuConverter 사용)
        return LinkuConverter.toLinkuResultDTO(
                userId,
                linku,
                usersLinku,
                linkuFolder,
                category,
                emotion,
                domain
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

