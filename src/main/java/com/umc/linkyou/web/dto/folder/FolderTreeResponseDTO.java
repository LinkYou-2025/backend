package com.umc.linkyou.web.dto.folder;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FolderTreeResponseDTO> children = new ArrayList<>();
}
