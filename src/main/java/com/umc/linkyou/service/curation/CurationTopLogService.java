package com.umc.linkyou.service.curation;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.log.CurationTopLog;
import com.umc.linkyou.web.dto.curation.CurationTopLogDTO;

import java.util.List;

public interface CurationTopLogService {
    List<CurationTopLog> getTop3LogsByCuration(Long curationId);
    List<String> getTopTagNamesByCuration(Long curationId);
    void calculateAndSaveTopLogs(Long userId, Curation curation);

    List<CurationTopLogDTO> getTopLogDtoByCuration(Long curationId);
}