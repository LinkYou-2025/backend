package com.umc.linkyou.web.dto.folder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderRequestDTO {
    private Long folderId;
    private String folderName;
    private Long categoryId;
    private String categoryName;
    private Long parentFolderId;
}
