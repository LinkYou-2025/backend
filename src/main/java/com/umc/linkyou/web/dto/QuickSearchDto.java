package com.umc.linkyou.web.dto;

import lombok.*;

import java.util.List;

public class QuickSearchDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuickSearchResult {
        private String searchKeyword;
        private List<String> recentKeywords;
        private List<LinkDetailDto> links;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LinkDetailDto {
        private String title;
        private String summary;
        private String source;
        private String url;
        private String favicon;
    }
}
