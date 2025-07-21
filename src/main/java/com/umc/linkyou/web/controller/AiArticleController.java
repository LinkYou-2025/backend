package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.code.status.SuccessStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.repository.AiArticleRepository;
import com.umc.linkyou.service.AiArticleService;
import com.umc.linkyou.web.dto.AiArticleResponsetDTO;
import com.umc.linkyou.web.dto.DomainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aiarticle")
@RequiredArgsConstructor
public class AiArticleController {

    final private AiArticleService aiArticleService;
    final private AiArticleRepository aiArticleRepository;

    @PostMapping("/{linkuid}")
    public ResponseEntity<ApiResponse<AiArticleResponsetDTO.AiArticleResultDTO>> saveAiArticle(
            @PathVariable("linkuid") Long linkuId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponse.onFailure(
                            ErrorStatus._INVALID_TOKEN.getCode(),
                            ErrorStatus._INVALID_TOKEN.getMessage(),
                            null
                    )
            );
        }
        Long userId = userDetails.getUsers().getId();
        boolean exists = aiArticleRepository.existsByLinku_LinkuId(linkuId);
        AiArticleResponsetDTO.AiArticleResultDTO result;
        if (!exists) {
            result = aiArticleService.saveAiArticle(linkuId, userId);
            // 201: 생성! (ApiResponse.of 쓰면 code/message도 생성용으로 가공 가능)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.of(SuccessStatus._CREATED, result));
        } else {
            result = aiArticleService.showAiArticle(linkuId, userId);
            // 200: 정상 조회!
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.of(SuccessStatus._OK, result));
        }
    }


}
