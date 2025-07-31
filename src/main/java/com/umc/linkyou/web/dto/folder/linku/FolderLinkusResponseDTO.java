package com.umc.linkyou.web.dto.folder.linku;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderLinkusResponseDTO {
    private List<FolderSummaryDTO> folders;
    private List<LinkuSummaryDTO> links;
    private String nextCursor;
}
