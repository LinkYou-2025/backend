package com.umc.linkyou.repository.LogRepository.custom;

import com.umc.linkyou.domain.enums.CurationTopLogType;
import com.umc.linkyou.domain.log.CurationTopLog;
import java.util.List;
import java.util.Optional;

public interface CurationTopLogCustomRepository {
    List<CurationTopLog> findTop3ByCurationId(Long curationId);
    CurationTopLog findTopEmotionLogByCurationId(Long curationId);

    List<CurationTopLog> findTopTagsByUserId(Long userId, int limit);
    List<CurationTopLog> findTop3EmotionLogsByCurationId(Long curationId);
}