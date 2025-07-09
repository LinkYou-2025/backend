package com.umc.linkyou.service;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;

public interface LinkuService {
    LinkuResponseDTO.LinkuResultDTO createLinku(Long userId, LinkuRequestDTO.LinkuCreateDTO dto) ;
}
