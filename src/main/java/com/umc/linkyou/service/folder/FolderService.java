package com.umc.linkyou.service.folder;

import com.umc.linkyou.web.dto.folder.*;

import java.util.List;

public interface FolderService {
    FolderResponseDTO createFolder(Long userId, Long parentFolderId, FolderCreateRequestDTO req);

    FolderResponseDTO updateFolder(Long userId, Long folderId, FolderUpdateRequestDTO req);

    FolderResponseDTO deleteFolder(Long userId, Long folderId);

    // 폴더 트리 전체 조회
    List<FolderTreeResponseDTO> getMyFolderTree(Long userId);

    // 자식 폴더 목록 조회
    List<FolderListResponseDTO> getSubFolders(Long userId, Long parentFolderId);

    // 북마크 설정/해제
    FolderResponseDTO updateBookmark(Long userId, Long folderId, Boolean isBookmarked);
}
