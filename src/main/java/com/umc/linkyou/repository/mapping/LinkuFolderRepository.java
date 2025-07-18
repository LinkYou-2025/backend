package com.umc.linkyou.repository.mapping;

import com.umc.linkyou.domain.mapping.LinkuFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkuFolderRepository  extends JpaRepository<LinkuFolder, Long> {
    Optional<Object> findById(long l);
}
