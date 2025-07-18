package com.umc.linkyou.repository.UsersFolderRepository;

import com.umc.linkyou.domain.folder.Folder;

import java.util.List;

public interface UsersFolderRepositoryCustom {
    List<Folder> searchFolders(
            Long userId,
            Long categoryId,
            Long parentFolderId,
            String folderName,
            Boolean isBookmarked,
            Boolean isShared
    );
}