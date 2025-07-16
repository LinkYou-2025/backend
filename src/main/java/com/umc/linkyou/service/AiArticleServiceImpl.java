package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.GeneralException;
import com.umc.linkyou.domain.*;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.classification.Job;
import com.umc.linkyou.domain.classification.Situation;
import com.umc.linkyou.domain.mapping.SituationJob;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.openApi.OpenAISummaryUtil;
import com.umc.linkyou.openApi.SummaryAnalysisResultDTO;
import com.umc.linkyou.repository.*;
import com.umc.linkyou.repository.EmotionRepository;
import com.umc.linkyou.repository.classification.CategoryRepository;
import com.umc.linkyou.repository.classification.SituationRepository;
import com.umc.linkyou.repository.mapping.SituationJobRepository;
import com.umc.linkyou.repository.mapping.UsersLinkuRepository;
import com.umc.linkyou.web.dto.AiArticleResponsetDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiArticleServiceImpl implements AiArticleService {

    private final UsersLinkuRepository usersLinkuRepository;
    private final UserRepository userRepository;
    private final SituationJobRepository situationJobRepository;
    private final EmotionRepository emotionRepository;
    private final CategoryRepository categoryRepository;
    private final SituationRepository situationRepository;
    private final AiArticleRepository aiArticleRepository;
    private final OpenAISummaryUtil openAISummaryUtil;

    @Override
    @Transactional
    public AiArticleResponsetDTO.AiArticleResultDTO saveAiArticle(Long linkuId, Long userId) {

        UsersLinku usersLinku = usersLinkuRepository.findById(linkuId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));
        Linku linku = usersLinku.getLinku();
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));
        Job job = user.getJob();

        // 1. Job에 해당하는 Situation 조회
        List<Situation> situations = situationJobRepository.findAllByJob(job).stream()
                .map(SituationJob::getSituation)
                .toList();
        if (situations.isEmpty()) throw new GeneralException(ErrorStatus._DOMAIN_NOT_FOUND);

        // 2. Emotion, Category 전체 조회
        List<Emotion> emotions = emotionRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        if (emotions.isEmpty()) throw new GeneralException(ErrorStatus._EMOTION_NOT_FOUND);
        if (categories.isEmpty()) throw new GeneralException(ErrorStatus._CATEGORY_NOT_FOUND);

        // 3. OpenAI 호출 (상황/감정/카테고리 리스트 Entity 그대로 전달)
        SummaryAnalysisResultDTO result;
        try {
            result = openAISummaryUtil.getFullAnalysis(
                    linku.getLinku(), situations, emotions, categories
            );
        } catch (IOException e) {
            log.error("[AI JSON 파싱 실패 또는 응답 오류]: {}", e.getMessage(), e);
            throw new GeneralException(ErrorStatus._AI_INVALID_RESPONSE); // 🔄 새로운 에러 정의 권장
        }

        // 4. id 기반 Entity 조인
        Situation selectedSituation = situationRepository.findById(result.getSituationId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._SITUATION_NOT_FOUND));
        Emotion selectedEmotion = emotionRepository.findById(result.getEmotionId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._EMOTION_NOT_FOUND));
        Category selectedCategory = categoryRepository.findById(result.getCategoryId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._CATEGORY_NOT_FOUND));

        // 5. 저장
        AiArticle article = AiArticle.builder()
                .title(result.getTitle())
                .summary(result.getSummary())
                .aiFeeling(selectedEmotion.getName())
                .aiCategory(selectedCategory.getName())
                .situation(selectedSituation)
                .usersLinku(usersLinku)
                .imgUrl(usersLinku.getImageUrl())
                .keyword(result.getKeywords())
                .build();

        AiArticle saved = aiArticleRepository.save(article);

        // 6. 반환 DTO
        return AiArticleResponsetDTO.AiArticleResultDTO.builder()
                .id(saved.getId())
                .linku(linku)
                .situation(selectedSituation)
                .emotion(selectedEmotion)
                .title(saved.getTitle())
                .summary(saved.getSummary())
                .imgUrl(saved.getImgUrl())
                .aiCategory(saved.getAiCategory())
                .aiFeeling(saved.getAiFeeling())
                .keyword(saved.getKeyword())
                .build();
    }
}
