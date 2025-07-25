package com.umc.linkyou.repository.mapping;

import com.umc.linkyou.domain.mapping.SituationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SituationCategoryRepository extends JpaRepository<SituationCategory, Long> {
    List<SituationCategory> findBySituation_Id(Long situationId);
}
