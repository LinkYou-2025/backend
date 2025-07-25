package com.umc.linkyou.repository.mapping;


import com.umc.linkyou.domain.classification.Job;
import com.umc.linkyou.domain.mapping.SituationJob;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SituationJobRepository extends CrudRepository<SituationJob, Long> {
    List<SituationJob> findAllByJob(Job job);

    Optional<SituationJob> findBySituation_IdAndJob_Id(Long situationId, Long jobId);
}
