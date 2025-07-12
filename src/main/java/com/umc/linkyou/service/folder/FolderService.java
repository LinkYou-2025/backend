package com.umc.linkyou.service.folder;

import com.umc.linkyou.web.dto.folder.FolderCreateRequestDTO;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderTreeResponseDTO;
import java.util.List;

public interface FolderService {
    FolderResponseDTO createFolder(Long userId, FolderCreateRequestDTO req);

    // 폴더 트리 전체 조회
    List<FolderTreeResponseDTO> getMyFolderTree(Long userId);

    // 중분류별 소분류 폴더 조회
    List<FolderResponseDTO> getSubFolders(Long userId, Long parentFolderId);
}
