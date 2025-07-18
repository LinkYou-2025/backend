package com.umc.linkyou.converter;

import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderTreeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class FolderConverter {
    public FolderResponseDTO toFolderResponseDTO(Folder folder) {
        if (folder == null) {
            return null;
        }
        Category category = folder.getCategory();
        return FolderResponseDTO.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .categoryId(category != null ? category.getCategoryId() : null)
                .categoryName(category != null ? category.getCategoryName() : null)
                .parentFolderId(folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : null)
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .build();
    }

    public FolderTreeResponseDTO toFolderTreeDTO(Folder folder) {
        FolderTreeResponseDTO dto = new FolderTreeResponseDTO();

        dto.setFolderId(folder.getFolderId());
        dto.setFolderName(folder.getFolderName());

        Category category = folder.getCategory();
        if (category != null) {
            dto.setCategoryId(category.getCategoryId());
        }
        return dto;
    }
}

