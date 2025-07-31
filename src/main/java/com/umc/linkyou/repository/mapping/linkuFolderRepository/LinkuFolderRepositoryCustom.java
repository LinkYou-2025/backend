package com.umc.linkyou.repository.mapping.linkuFolderRepository;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.LinkuFolder;

import java.util.List;

public interface LinkuFolderRepositoryCustom {
    List<LinkuFolder> findByFolder(Folder folder);
}
