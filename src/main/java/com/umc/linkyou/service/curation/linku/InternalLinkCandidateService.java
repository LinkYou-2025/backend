package com.umc.linkyou.service.curation.linku;

import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;
import java.util.List;

public interface InternalLinkCandidateService {
    List<RecommendedLinkResponse> getInternalCandidates(Long userId,Long curationId, int limit);

    // 항상 2개만 반환
    default List<RecommendedLinkResponse> getTop2SimilarInternalLinks(Long userId, Long curationId) {
        return getInternalCandidates(userId, curationId, 2);
    }
}