package com.umc.linkyou.repository.LogRepository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.log.QEmotionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class EmotionLogCustomRepositoryImpl implements EmotionLogCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QEmotionLog emotionLog = QEmotionLog.emotionLog;

    @Override
    public Long findTopEmotionIdByUserAndMonth(Long userId, String month) {
        LocalDateTime start = LocalDate.parse(month + "-01").atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        return queryFactory
                .select(emotionLog.emotion.emotionId)
                .from(emotionLog)
                .where(
                        emotionLog.user.id.eq(userId),
                        emotionLog.createdAt.between(start, end)
                )
                .groupBy(emotionLog.emotion.emotionId)
                .orderBy(emotionLog.count().desc())
                .limit(1)
                .fetchOne();
    }
}

