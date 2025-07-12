package com.umc.linkyou.repository;

import com.umc.linkyou.domain.folder.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}