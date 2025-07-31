package com.umc.linkyou.repository;

import com.umc.linkyou.domain.folder.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByParentFolder_FolderId(Long parentFolderId);
}