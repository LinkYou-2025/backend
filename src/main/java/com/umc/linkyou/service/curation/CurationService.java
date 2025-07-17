package com.umc.linkyou.service.curation;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.web.dto.curation.CreateCurationRequest;
import com.umc.linkyou.web.dto.curation.CurationDetailResponse;

public interface CurationService {
    Curation createCuration(Long userId, CreateCurationRequest request);
    CurationDetailResponse getCurationDetail(Long curationId);
}