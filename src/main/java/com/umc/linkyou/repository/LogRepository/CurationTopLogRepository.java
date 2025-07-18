package com.umc.linkyou.repository.LogRepository;

import com.umc.linkyou.domain.log.CurationTopLog;
import com.umc.linkyou.repository.LogRepository.custom.CurationTopLogCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationTopLogRepository extends JpaRepository<CurationTopLog, Long>, CurationTopLogCustomRepository {
}