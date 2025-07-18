package com.umc.linkyou.repository.LogRepository;

import com.umc.linkyou.domain.log.SituationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationLogRepository extends JpaRepository<SituationLog, Long> {
}