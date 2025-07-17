package com.umc.linkyou.repository.classification;

import com.umc.linkyou.domain.classification.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {
}
