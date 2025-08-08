package com.umc.linkyou.service.curation.linku;

import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;
import java.util.List;

public interface ExternalRecommendService {
    List<RecommendedLinkResponse> getExternalRecommendations(Long userId, Long curationId, int limit);
}
