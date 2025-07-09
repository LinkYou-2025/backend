package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.LinkuService;
import com.umc.linkyou.service.UserService;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/linku")
@RequiredArgsConstructor
public class LinkuController {

    private final LinkuService linkuService;

    @PostMapping("/")
    public ApiResponse<LinkuResponseDTO.LinkuResultDTO> createLinku(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody LinkuRequestDTO.LinkuCreateDTO linkuCreateDTO) {
        if (userDetails == null) {
            return ApiResponse.onFailure(ErrorStatus._INVALID_TOKEN.getCode(), ErrorStatus._INVALID_TOKEN.getMessage(), null);
        }

        Long userId = userDetails.getUsers().getId().longValue();
        LinkuResponseDTO.LinkuResultDTO result = linkuService.createLinku(userId, linkuCreateDTO);
        return ApiResponse.onSuccess(result); //responseDTO다시 써야 함
    }

}
