package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.log.EmotionLog;
import com.umc.linkyou.domain.log.SituationLog;
import com.umc.linkyou.domain.mapping.SituationJob;

import java.time.LocalDateTime;

public class LogConverter {

    /**
     * 사용자 ID, 상황 ID로 SituationLog 엔티티 생성
     */
    public static SituationLog toSituationLog(Users user, SituationJob situationJob) {
        return SituationLog.builder()
                .user(user)
                .situationJob(situationJob)
                .build();
    }

    /**
     * 사용자 ID, 감정 ID로 EmotionLog 엔티티 생성
     */
    public static EmotionLog toEmotionLog(Users user, Emotion emotion) {
        return EmotionLog.builder()
                .user(user)
                .emotion(emotion)
                .build();
    }
}
