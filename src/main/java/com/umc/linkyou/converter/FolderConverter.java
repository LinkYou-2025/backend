package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Category;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;

public class FolderConverter {
    public static FolderResponseDTO toFolderTreeDTO(Folder folder) {
        return FolderResponseDTO.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .categoryId(folder.getCategory().getCategoryId())
                .build();
    }
}

