package com.umc.linkyou.web.dto.category;

import lombok.*;

@Getter
@Setter
@Builder
public class CategoryListResponseDTO {
    private Long categoryId;
    private String categoryName;
}
