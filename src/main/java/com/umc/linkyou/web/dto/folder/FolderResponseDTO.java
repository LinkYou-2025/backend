package com.umc.linkyou.web.dto.folder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class FolderResponseDTO {
    private Long folderId;
    private String folderName;
    private Long categoryId;
    private String categoryName;
    private Long parentFolderId;
}
