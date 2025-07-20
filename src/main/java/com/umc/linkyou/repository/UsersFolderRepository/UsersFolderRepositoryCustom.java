package com.umc.linkyou.repository.UsersFolderRepository;

import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;

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

    UsersFolder findByUserIdAndFolderId(Long userId, Long folderId);
}