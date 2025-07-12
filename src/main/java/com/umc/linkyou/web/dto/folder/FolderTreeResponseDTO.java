package com.umc.linkyou.web.dto.folder;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderTreeResponseDTO {
    private Long folderId;
    private String folderName;
    private Long categoryId;
    @Builder.Default
    private List<FolderTreeResponseDTO> children = new ArrayList<>();
}
