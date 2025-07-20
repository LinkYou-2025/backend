package com.umc.linkyou.converter;

import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.mapping.folder.UsersCategoryColor;
import com.umc.linkyou.web.dto.category.CategoryListResponseDTO;
import com.umc.linkyou.web.dto.category.UserCategoryColorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryListResponseDTO toCategoryListResponseDTO(Category category) {
        return CategoryListResponseDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public UserCategoryColorResponseDTO toUserCategoryColorResponseDTO(UsersCategoryColor ucc) {
        return UserCategoryColorResponseDTO.builder()
                .categoryId(ucc.getCategory().getCategoryId())
                .fcolor(ucc.getFcolor().getColorCode())
                .build();
    }
}
