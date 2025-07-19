package com.umc.linkyou.repository.categoryRepository;

import com.umc.linkyou.domain.folder.Fcolor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcolorRepository extends JpaRepository<Fcolor, Long>, FcolorRepositoryCustom {
}

