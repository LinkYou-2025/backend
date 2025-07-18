package com.umc.linkyou.repository.LogRepository.custom;

public interface EmotionLogCustomRepository {
    Long findTopEmotionIdByUserAndMonth(Long userId, String month);
}