package com.umc.linkyou.repository.UsersFolderRepository;

import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersFolderRepository extends JpaRepository<UsersFolder, Long>, UsersFolderRepositoryCustom {
}