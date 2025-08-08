package com.umc.linkyou.service.curation;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.web.dto.curation.CurationTopLogDTO;

import java.util.List;

public interface CurationTopLogService {
    List<String> getTopTagNamesByCuration(Long curationId);
    void calculateAndSaveTopLogs(Long userId, Curation curation);

    List<CurationTopLogDTO> getTopLogDtoByCuration(Long curationId);
}