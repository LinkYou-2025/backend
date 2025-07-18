package com.umc.linkyou.repository;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {
    boolean existsByUserAndMonth(Users user, String month);
    Optional<Curation> findTopByUser_IdOrderByCreatedAtDesc(Long userId);
}