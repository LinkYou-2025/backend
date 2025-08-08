package com.umc.linkyou.service.curation.linku;

import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurationRecommendBuilderServiceImpl implements CurationRecommendBuilderService {

    private final InternalLinkCandidateService internalLinkCandidateService;
    private final ExternalRecommendService externalRecommendService;

    @Override
    public List<RecommendedLinkResponse> buildRecommendedLinks(Long userId, Long curationId) {
        // 1. 내부 추천
        List<RecommendedLinkResponse> internalLinks =
                internalLinkCandidateService.getInternalCandidates(userId, curationId, 4);

        int externalLimit = 9 - internalLinks.size();

        // 2. 외부 추천
        List<RecommendedLinkResponse> externalLinks =
                externalRecommendService.getExternalRecommendations(userId, curationId, externalLimit);

        // 3. 합치기
        List<RecommendedLinkResponse> all = new ArrayList<>();
        all.addAll(internalLinks);
        all.addAll(externalLinks);
        return all;
    }
}

