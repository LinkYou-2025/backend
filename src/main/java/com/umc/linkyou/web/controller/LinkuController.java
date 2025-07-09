package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/linku")
@RequiredArgsConstructor
public class LinkuController {
    @PostMapping("/")
    public ApiResponse<LinkuRequestDTO.LinkuCreateDTO> createLinku(@RequestBody LinkuRequestDTO.LinkuCreateDTO linkuCreateDTO) {


        return ApiResponse.onSuccess(linkuCreateDTO); //responseDTO다시 써야 함
    }

}
