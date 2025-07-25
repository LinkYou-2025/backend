package com.umc.linkyou.utils;

import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmotionSimilarityUtil {

    private static final Map<Pair<Long, Long>, Integer> EMOTION_SIMILARITY_MAP;

    static {
        Map<Pair<Long, Long>, Integer> map = new HashMap<>();

        // 정확 일치 (3점) — Long 타입으로 감정 ID 관리
        for (long id = 1L; id <= 6L; id++) {
            map.put(Pair.of(id, id), 3);
        }

        // 유사 감정 (2점)
        map.put(Pair.of(1L, 3L), 2); map.put(Pair.of(3L, 1L), 2); // 즐거움 ↔ 설렘
        map.put(Pair.of(5L, 6L), 2); map.put(Pair.of(6L, 5L), 2); // 짜증 ↔ 분노

        // 약한 연관 (1점)
        map.put(Pair.of(2L, 4L), 1); map.put(Pair.of(4L, 2L), 1); // 평온 ↔ 슬픔

        EMOTION_SIMILARITY_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * userEmotionId, selectedEmotionId 간 감정 유사도 점수 반환
     * 없으면 0 반환
     */
    public static int getSimilarityScore(Long userEmotionId, Long selectedEmotionId) {
        if (userEmotionId == null || selectedEmotionId == null) {
            return 0;
        }
        return EMOTION_SIMILARITY_MAP.getOrDefault(Pair.of(userEmotionId, selectedEmotionId), 0);
    }
}
