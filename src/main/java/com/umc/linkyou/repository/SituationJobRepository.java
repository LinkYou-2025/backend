package com.umc.linkyou.repository;

import com.umc.linkyou.domain.mapping.SituationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituationJobRepository extends JpaRepository<SituationJob, Long> {
}
