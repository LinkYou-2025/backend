package com.umc.linkyou.web.controller;

import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.category.CategoryService;
import com.umc.linkyou.web.dto.category.CategoryListResponseDTO;
import com.umc.linkyou.web.dto.category.UpdateCategoryColorRequestDTO;
import com.umc.linkyou.web.dto.category.UserCategoryColorResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 목록 조회
    @GetMapping
    @Operation(summary = "카테고리 목록 조회")
    public ResponseEntity<List<CategoryListResponseDTO>> getCategoryList(
    ) {
        List<CategoryListResponseDTO> categoryList = categoryService.getCategories();
        return ResponseEntity.ok(categoryList);
    }

    // 유저 카테고리(중분류 폴더) 색상 수정
    @PutMapping("/{categoryId}/color")
    @Operation(summary = "유저 카테고리(중분류 폴더) 색상 수정")
    public ResponseEntity<UserCategoryColorResponseDTO> updateUserCategoryColor(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId,
            @RequestBody UpdateCategoryColorRequestDTO request
    ) {
        UserCategoryColorResponseDTO userCategoryColor =
                categoryService.updateUserCategoryColor(userDetails.getUsers().getId(), categoryId, request);
        return ResponseEntity.ok(userCategoryColor);
    }
}
