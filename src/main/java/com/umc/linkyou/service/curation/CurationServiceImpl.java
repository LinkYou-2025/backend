package com.umc.linkyou.service.curation;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.CurationMent;
import com.umc.linkyou.domain.enums.CurationTopLogType;
import com.umc.linkyou.domain.log.CurationTopLog;
import com.umc.linkyou.repository.CurationMentRepository;
import com.umc.linkyou.web.dto.curation.CreateCurationRequest;
import com.umc.linkyou.repository.LogRepository.CurationTopLogRepository;
import com.umc.linkyou.repository.CurationRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.web.dto.curation.CurationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    private final UserRepository userRepository;
    private final CurationRepository curationRepository;
    private final CurationTopLogService curationTopLogService;

    /**
     * 유저의 큐레이션을 생성하고, 감정/상황 로그 기반 top3 태그를 계산해 저장한다.
     */
    @Override
    @Transactional
    public Curation createCuration(Long userId, CreateCurationRequest request) {
        // 1. 사용자 조회
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 큐레이션 객체 생성
        Curation curation = Curation.builder()
                .user(user)
                .month(request.getMonth())
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        // 3. 저장
        curationRepository.save(curation);

        // 4. 상위 3개 로그 저장
        curationTopLogService.calculateAndSaveTopLogs(userId, curation);

        return curation;
    }

    private final CurationTopLogRepository curationTopLogRepository;
    private final CurationMentRepository curationMentRepository;

    @Override
    @Transactional(readOnly = true)
    public CurationDetailResponse getCurationDetail(Long curationId) {
        Curation curation = curationRepository.findById(curationId)
                .orElseThrow(() -> new IllegalArgumentException("큐레이션 없음"));

        // 0. 사용자 닉네임 가져오기
        String nickname = curation.getUser().getNickName(); // 연관관계 매핑 필요

        // 1. 상위 태그 3개 조회
        List<CurationTopLog> topLogs = curationTopLogRepository.findTop3ByCurationId(curationId);
        List<String> tagNames = topLogs.stream()
                .map(CurationTopLog::getTagName)
                .toList();

        // 2. topLogs 중 type = EMOTION 인 것 중 count 가장 높은 1개 추출
        CurationTopLog topEmotionLog = topLogs.stream()
                .filter(log -> log.getType() == CurationTopLogType.EMOTION)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("감정 기반 태그 없음"));

        Long emotionId = topEmotionLog.getRefId();

        // 3. 해당 감정 ID로 멘트 랜덤 조회
        List<CurationMent> mentList = curationMentRepository.findAllByEmotion_EmotionId(emotionId);
        if (mentList.isEmpty()) {
            throw new IllegalArgumentException("해당 감정에 대한 멘트 없음");
        }
        CurationMent ment = mentList.get(new Random().nextInt(mentList.size()));

        // 4. (닉네임) 치환
        String header = ment.getHeaderText().replace("(닉네임)", nickname);
        String footer = ment.getFooterText().replace("(닉네임)", nickname);

        // 5. 응답 반환
        return CurationDetailResponse.builder()
                .curationId(curation.getCurationId())
                .month(curation.getMonth())
                .topTags(tagNames)
                .headerMent(header)
                .footerMent(footer)
                .build();
    }
}
