package com.umc.linkyou.repository.curationLinkuRepository;

import com.umc.linkyou.domain.mapping.UsersLinku;
import java.util.List;

public interface UsersLinkuRepositoryCustom {
    List<UsersLinku> findRecentLinkCandidatesByUser(Long userId, int limit);
}