package com.umc.linkyou.repository;

import com.umc.linkyou.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalDouble;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Optional<Emotion> findById(Long emotionId);
}
