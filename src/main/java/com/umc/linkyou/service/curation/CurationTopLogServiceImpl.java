package com.umc.linkyou.service.curation;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.log.CurationTopLog;
import com.umc.linkyou.domain.log.QEmotionLog;
import com.umc.linkyou.domain.log.QSituationLog;
import com.umc.linkyou.domain.enums.CurationTopLogType;
import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.repository.LogRepository.CurationTopLogRepository;
import com.umc.linkyou.repository.LogRepository.EmotionLogRepository;
import com.umc.linkyou.repository.LogRepository.SituationLogRepository;
import com.umc.linkyou.repository.EmotionRepository;
import com.umc.linkyou.repository.mapping.SituationJobRepository;
import com.umc.linkyou.web.dto.curation.CurationTopLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurationTopLogServiceImpl implements CurationTopLogService {

    private final CurationTopLogRepository curationTopLogRepository;
    private final EmotionLogRepository emotionLogRepository;
    private final SituationLogRepository situationLogRepository;
    private final EmotionRepository emotionRepository;
    private final SituationJobRepository situationJobRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CurationTopLog> getTop3LogsByCuration(Long curationId) {
        return curationTopLogRepository.findTop3ByCurationId(curationId);
    }

    @Override
    public List<String> getTopTagNamesByCuration(Long curationId) {
        return curationTopLogRepository.findTop3ByCurationId(curationId).stream()
                .map(CurationTopLog::getTagName)
                .collect(Collectors.toList());
    }

    @Transactional
    public void calculateAndSaveTopLogs(Long userId, Curation curation) {
        QEmotionLog emotionLog = QEmotionLog.emotionLog;
        QSituationLog situationLog = QSituationLog.situationLog;

        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();

        List<Tuple> emotionCounts = queryFactory
                .select(emotionLog.emotion.emotionId, emotionLog.count())
                .from(emotionLog)
                .where(
                        emotionLog.user.id.eq(userId),
                        emotionLog.createdAt.goe(startOfMonth)
                )
                .groupBy(emotionLog.emotion.emotionId)
                .fetch();

        List<Tuple> situationCounts = queryFactory
                .select(situationLog.situationJob.id, situationLog.count())
                .from(situationLog)
                .where(
                        situationLog.user.id.eq(userId),
                        situationLog.createdAt.goe(startOfMonth)
                )
                .groupBy(situationLog.situationJob.id)
                .fetch();

        List<CurationTopLog> logs = new ArrayList<>();

        for (Tuple row : emotionCounts) {
            Long refId = row.get(emotionLog.emotion.emotionId);
            Integer count = row.get(emotionLog.count()).intValue();
            String tagName = emotionRepository.findById(refId)
                    .orElseThrow(() -> new IllegalArgumentException("Emotion not found")).getName();

            logs.add(CurationTopLog.builder()
                    .curation(curation)
                    .type(CurationTopLogType.EMOTION)
                    .refId(refId)
                    .count(count)
                    .tagName(tagName)
                    .build());
        }

        for (Tuple row : situationCounts) {
            Long refId = row.get(situationLog.situationJob.id);
            Integer count = row.get(situationLog.count()).intValue();
            String tagName = situationJobRepository.findById(refId)
                    .orElseThrow(() -> new IllegalArgumentException("Not found"))
                    .getSituation()
                    .getName();

            logs.add(CurationTopLog.builder()
                    .curation(curation)
                    .type(CurationTopLogType.SITUATION)
                    .refId(refId)
                    .count(count)
                    .tagName(tagName)
                    .build());
        }

        curationTopLogRepository.saveAll(logs);
    }

    @Override
    public List<CurationTopLogDTO> getTopLogDtoByCuration(Long curationId) {
        return curationTopLogRepository.findTop3ByCurationId(curationId).stream()
                .map(log -> CurationTopLogDTO.builder()
                        .type(log.getType())
                        .tagName(log.getTagName())
                        .count(log.getCount())
                        .build())
                .collect(Collectors.toList());
    }
}

