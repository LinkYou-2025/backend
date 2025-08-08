package com.umc.linkyou.service.curation.utils;

import java.util.Map;

public class EmotionSimilarityTable {
    private static final Map<String, Map<String, Double>> similarityMap = Map.of(
            "슬픔", Map.of("분노", 0.6, "짜증", 0.5, "설렘", 0.2, "즐거움", 0.1),
            "분노", Map.of("짜증", 0.8, "슬픔", 0.6, "즐거움", 0.1),
            "짜증", Map.of("분노", 0.8, "슬픔", 0.5, "설렘", 0.2),
            "설렘", Map.of("즐거움", 0.7, "짜증", 0.2),
            "즐거움", Map.of("설렘", 0.7, "짜증", 0.1),
            "평온", Map.of() // 유사도 0으로 처리
    );

    public static double getSimilarity(String emotionA, String emotionB) {
        return similarityMap.getOrDefault(emotionA, Map.of()).getOrDefault(emotionB, 0.0);
    }
}
