package com.umc.linkyou.web.dto.linku;

import com.umc.linkyou.domain.mapping.UsersLinku;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class LinkuInternalDTO {
    @Getter @Setter @Builder
    public static class ScoredLinkuDTO {
        private UsersLinku userLinku;
        private int emotionScore;
        private int situationScore;
        private int totalScore;
    }
}
