package com.umc.linkyou.service.Linku;

import com.umc.linkyou.web.dto.QuickSearchDto;

public interface SearchService {
    public QuickSearchDto.QuickSearchResult quickSearch(String keyword, Long userId);
}
