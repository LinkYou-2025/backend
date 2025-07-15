package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface LinkuService {
    LinkuResponseDTO.LinkuResultDTO createLinku(Long userId, LinkuRequestDTO.LinkuCreateDTO dto, MultipartFile image) ;

    ResponseEntity<ApiResponse<LinkuResponseDTO.LinkuIsExistDTO>> existLinku(Long userId, String url);
}
