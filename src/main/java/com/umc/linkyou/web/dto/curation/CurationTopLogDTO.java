package com.umc.linkyou.web.dto.curation;

import com.umc.linkyou.domain.enums.CurationTopLogType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurationTopLogDTO {
    private CurationTopLogType type;
    private String tagName;
    private int count;
}
