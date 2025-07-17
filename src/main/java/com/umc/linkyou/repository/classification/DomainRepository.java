package com.umc.linkyou.repository.classification;

import com.umc.linkyou.domain.classification.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByDomainTail(String domainTail);

    Optional<Domain> findById(long l);
}
