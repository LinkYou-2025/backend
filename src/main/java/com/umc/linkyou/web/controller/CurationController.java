package com.umc.linkyou.web.controller;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.service.curation.CurationService;
import com.umc.linkyou.service.curation.CurationTopLogService;
import com.umc.linkyou.web.dto.curation.CreateCurationRequest;
import com.umc.linkyou.web.dto.curation.CreateCurationResponse;
import com.umc.linkyou.web.dto.curation.CurationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curations")
public class CurationController {

    private final CurationTopLogService curationTopLogService;
    private final CurationService curationService;

    @GetMapping("/top-log/{curationId}")
    public List<String> getTopTags(@PathVariable Long curationId) {
        return curationTopLogService.getTopTagNamesByCuration(curationId);
    }

    /**
     * 큐레이션 생성 요청 (Top3 감정/상황 자동 분석 포함)
     */
    @PostMapping("/generate/{userId}")
    public ResponseEntity<CreateCurationResponse> createCuration(
            @PathVariable Long userId,
            @RequestBody CreateCurationRequest request) {

        Curation created = curationService.createCuration(userId, request);

        CreateCurationResponse response = CreateCurationResponse.builder()
                .curationId(created.getCurationId())
                .month(created.getMonth())
                .thumbnailUrl(created.getThumbnailUrl())
                .createdAt(created.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 큐레이션 상세 조회 API
     */
    @GetMapping("/detail/{curationId}")
    public ResponseEntity<CurationDetailResponse> getCurationDetail(@PathVariable Long curationId) {
        CurationDetailResponse response = curationService.getCurationDetail(curationId);
        return ResponseEntity.ok(response);
    }
}