package com.umc.linkyou.repository.LogRepository.custom;

import com.umc.linkyou.domain.log.CurationTopLog;
import java.util.List;

public interface CurationTopLogCustomRepository {
    List<CurationTopLog> findTop3ByCurationId(Long curationId);
}