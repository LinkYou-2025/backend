package com.umc.linkyou.service.curation;

import com.umc.linkyou.web.dto.curation.LikedCurationResponse;

import java.util.List;

public interface CurationLikeService {
    void likeCuration(Long userId, Long curationId);
    void unlikeCuration(Long userId, Long curationId);
    boolean isLiked(Long userId, Long curationId);
    List<LikedCurationResponse> getRecentLikedCurations(Long userId);
}
