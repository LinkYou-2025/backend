package com.umc.linkyou.web.controller;

import com.umc.linkyou.converter.LinkuConverter;
import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.service.curation.CurationLikeService;
import com.umc.linkyou.service.curation.CurationService;
import com.umc.linkyou.service.curation.CurationTopLogService;
import com.umc.linkyou.service.curation.linku.CurationRecommendBuilderService;
import com.umc.linkyou.service.curation.linku.ExternalRecommendService;
import com.umc.linkyou.service.curation.linku.InternalLinkCandidateService;
import com.umc.linkyou.web.dto.curation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curations")
public class CurationController {

    private final CurationTopLogService curationTopLogService;
    private final CurationService curationService;
    private final CurationLikeService curationLikeService;
    private final CurationRecommendBuilderService curationRecommendBuilderService;
    private final InternalLinkCandidateService internalLinkCandidateService;

//    @GetMapping("/top-log/{curationId}")
//    public List<String> getTopTags(@PathVariable Long curationId) {
//        return curationTopLogService.getTopTagNamesByCuration(curationId);
//    }

    /**
     * 큐레이션 생성 요청 (Top3 감정/상황 자동 분석 포함)
     */
//    @PostMapping("/generate/{userId}")
//    public ResponseEntity<CreateCurationResponse> createCuration(
//            @PathVariable Long userId,
//            @RequestBody CreateCurationRequest request) {
//
//        Curation created = curationService.createCuration(userId, request);
//
//        CreateCurationResponse response = CreateCurationResponse.builder()
//                .curationId(created.getCurationId())
//                .month(created.getMonth())
//                .thumbnailUrl(created.getThumbnailUrl())
//                .createdAt(created.getCreatedAt())
//                .build();
//
//        return ResponseEntity.ok(response);
//    }
    // 자동생성 테스트
    @GetMapping("/batch/manual")
    public ResponseEntity<Void> triggerBatch() {
        curationService.generateMonthlyCurationForAllUsers();
        return ResponseEntity.ok().build();
    }

    /**
     * 큐레이션 상세 조회 API
     */
    @GetMapping("/detail/{curationId}")
    public ResponseEntity<CurationDetailResponse> getCurationDetail(@PathVariable Long curationId) {
        CurationDetailResponse response = curationService.getCurationDetail(curationId);
        return ResponseEntity.ok(response);
    }

    /**
     * 가장 최근 큐레이션 조회
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<CurationLatestResponse> getLatestCuration(@PathVariable Long userId) {
        return curationService.getLatestCuration(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build()); // or .notFound()
    }

    /**
     * 큐레이션 좋아요 등록
     */
    @PostMapping("/{curationId}/like")
    public ResponseEntity<Void> likeCuration(@PathVariable Long curationId, @RequestParam Long userId) {
        curationLikeService.likeCuration(userId, curationId);
        return ResponseEntity.ok().build();
    }
    /**
     * 큐레이션 좋아요 취소
     */
    @DeleteMapping("/{curationId}/like")
    public ResponseEntity<Void> unlikeCuration(@PathVariable Long curationId, @RequestParam Long userId) {
        curationLikeService.unlikeCuration(userId, curationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 큐레이션 좋아요 여부 확인
     */
    @GetMapping("/{curationId}/like")
    public ResponseEntity<CurationLikeStatusResponse> isLiked(@PathVariable Long curationId, @RequestParam Long userId) {
        boolean liked = curationLikeService.isLiked(userId, curationId);
        return ResponseEntity.ok(new CurationLikeStatusResponse(liked));
    }

    /**
     * 큐레이션 좋아요 리스트 가져오기
     */
    @GetMapping("/likes/recent")
    public ResponseEntity<List<LikedCurationResponse>> getRecentLikedCurations(@RequestParam Long userId) {
        return ResponseEntity.ok(curationLikeService.getRecentLikedCurations(userId));
    }

    /**
     * 큐레이션 링크 추천
     */
    @GetMapping("/recommend-links")
    public ResponseEntity<List<RecommendedLinkResponse>> getRecommendedLinks(
            @RequestParam Long userId,
            @RequestParam Long curationId) {

        List<RecommendedLinkResponse> recommendations =
                curationRecommendBuilderService.buildRecommendedLinks(userId, curationId);

        return ResponseEntity.ok(recommendations);
    }


    /**
     * 내부 링크 유사도 상위 2개
     */
    @GetMapping("/recommend-links/internal/top2")
    public ResponseEntity<List<RecommendedLinkResponse>> getInternalSimilarLinks(
            @RequestParam Long userId,
            @RequestParam Long curationId
    ) {
        List<RecommendedLinkResponse> result =
                internalLinkCandidateService.getTop2SimilarInternalLinks(userId, curationId);
        return ResponseEntity.ok(result);
    }
}