package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.LinkuService;
import com.umc.linkyou.service.UserService;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

        Long userId = userDetails.getUsers().getId();
        LinkuResponseDTO.LinkuResultDTO result = linkuService.createLinku(userId, linkuCreateDTO);
        return ApiResponse.onSuccess(result); //responseDTO다시 써야 함
    } //linku 생성 -> refactoring 필요

    @GetMapping("/exist")
    public ResponseEntity<ApiResponse<LinkuResponseDTO.LinkuIsExistDTO>> existLinku(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String url
    ){
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(ErrorStatus._INVALID_TOKEN.getCode(),ErrorStatus._INVALID_TOKEN.getMessage(), null));
        }
        Long userId = userDetails.getUsers().getId();
        return linkuService.existLinku(userId, url);
    } //linku 존재여부 확인

}
