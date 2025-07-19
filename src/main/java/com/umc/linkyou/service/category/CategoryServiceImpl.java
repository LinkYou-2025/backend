package com.umc.linkyou.service.category;

import com.umc.linkyou.converter.CategoryConverter;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.folder.Fcolor;
import com.umc.linkyou.domain.mapping.folder.UsersCategoryColor;
import com.umc.linkyou.repository.categoryRepository.FcolorRepository;
import com.umc.linkyou.repository.categoryRepository.UsersCategoryColorRepository;
import com.umc.linkyou.repository.classification.CategoryRepository;
import com.umc.linkyou.web.dto.category.CategoryListResponseDTO;
import com.umc.linkyou.web.dto.category.UpdateCategoryColorRequestDTO;
import com.umc.linkyou.web.dto.category.UserCategoryColorResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FcolorRepository fcolorRepository;
    private final UsersCategoryColorRepository usersCategoryColorRepository;
    private final CategoryConverter categoryConverter;

    // 카테고리 목록 조회
    public List<CategoryListResponseDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryConverter::toCategoryListResponseDTO).toList();
    }

    // 유저 카테고리(중분류 폴더) 색상 수정
    @Transactional
    public UserCategoryColorResponseDTO updateUserCategoryColor(
            Long userId,
            Long categoryId,
            UpdateCategoryColorRequestDTO request) {
        UsersCategoryColor ucc = usersCategoryColorRepository.searchCategoryColor(userId, categoryId);

        Fcolor fcolor = fcolorRepository.searchColorCode(request.getColorCode());
        
        ucc.setFcolor(fcolor);

        return categoryConverter.toUserCategoryColorResponseDTO(ucc);
    }
}
