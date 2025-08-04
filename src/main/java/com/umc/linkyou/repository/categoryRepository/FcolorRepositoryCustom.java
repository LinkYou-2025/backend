package com.umc.linkyou.repository.categoryRepository;

import com.umc.linkyou.domain.folder.Fcolor;

import java.util.Optional;

public interface FcolorRepositoryCustom {
    Fcolor searchColorCode(Long colorCode);
}
