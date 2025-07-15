package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.converter.LinkuConverter;
import com.umc.linkyou.service.LinkuService;
import com.umc.linkyou.service.UserService;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/linku")
@RequiredArgsConstructor
public class LinkuController {

    private final LinkuService linkuService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<LinkuResponseDTO.LinkuResultDTO> createLinku(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String linku,
            @RequestParam(required = false) String memo,
            @RequestParam(required = false) Long emotionId,
            @RequestParam(required = false) MultipartFile image
    ) {
        if (userDetails == null) {
            return ApiResponse.onFailure(
                    ErrorStatus._INVALID_TOKEN.getCode(),
                    ErrorStatus._INVALID_TOKEN.getMessage(),
                    null
            );
        }
        LinkuRequestDTO.LinkuCreateDTO linkuCreateDTO =
                LinkuConverter.toLinkuCreateDTO(linku, memo, emotionId);

        Long userId = userDetails.getUsers().getId();
        LinkuResponseDTO.LinkuResultDTO result = linkuService.createLinku(userId, linkuCreateDTO, image);
        return ApiResponse.onSuccess(result);
    }


    //linku 생성

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
