package com.umc.linkyou.repository.LogRepository;

import com.umc.linkyou.domain.log.EmotionLog;
import com.umc.linkyou.repository.LogRepository.custom.EmotionLogCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionLogRepository extends JpaRepository<EmotionLog, Long>, EmotionLogCustomRepository {
}