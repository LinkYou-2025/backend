package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.AiArticleService;
import com.umc.linkyou.web.dto.AiArticleResponsetDTO;
import com.umc.linkyou.web.dto.DomainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aiarticle")
@RequiredArgsConstructor
public class AiArticleController {

    final private AiArticleService aiArticleService;

    @PostMapping("/{linkuid}")
    public ApiResponse<AiArticleResponsetDTO.AiArticleResultDTO> saveAiArticle(
            @PathVariable("linkuid") Long linkuId,
            @RequestParam(required = false) String memo,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ApiResponse.onFailure(
                    ErrorStatus._INVALID_TOKEN.getCode(),
                    ErrorStatus._INVALID_TOKEN.getMessage(),
                    null
            );
        }
        Long userId = userDetails.getUsers().getId();
        AiArticleResponsetDTO.AiArticleResultDTO result = aiArticleService.saveAiArticle(linkuId,userId);
        return ApiResponse.onSuccess(result);
    }


}
