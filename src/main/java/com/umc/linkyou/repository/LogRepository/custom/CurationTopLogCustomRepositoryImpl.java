package com.umc.linkyou.repository.LogRepository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.log.CurationTopLog;
import com.umc.linkyou.domain.log.QCurationTopLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurationTopLogCustomRepositoryImpl implements CurationTopLogCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CurationTopLog> findTop3ByCurationId(Long curationId) {
        QCurationTopLog log = QCurationTopLog.curationTopLog;

        return queryFactory
                .selectFrom(log)
                .where(log.curation.curationId.eq(curationId))
                .orderBy(log.count.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public CurationTopLog findTopEmotionLogByCurationId(Long curationId) {
        QCurationTopLog log = QCurationTopLog.curationTopLog;

        return queryFactory
                .selectFrom(log)
                .where(
                        log.curation.curationId.eq(curationId),
                        log.type.eq(com.umc.linkyou.domain.enums.CurationTopLogType.EMOTION)
                )
                .orderBy(log.count.desc())
                .limit(1)
                .fetchOne(); // 하나만 조회
    }

    @Override
    public List<CurationTopLog> findTopTagsByUserId(Long userId, int limit) {
        QCurationTopLog log = QCurationTopLog.curationTopLog;

        return queryFactory
                .selectFrom(log)
                .where(log.curation.user.id.eq(userId))
                .orderBy(log.count.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<CurationTopLog> findTop3EmotionLogsByCurationId(Long curationId) {
        QCurationTopLog log = QCurationTopLog.curationTopLog;

        return queryFactory
                .selectFrom(log)
                .where(
                        log.curation.curationId.eq(curationId),
                        log.type.eq(com.umc.linkyou.domain.enums.CurationTopLogType.EMOTION)
                )
                .orderBy(log.count.desc())
                .limit(3)
                .fetch();
    }


}
