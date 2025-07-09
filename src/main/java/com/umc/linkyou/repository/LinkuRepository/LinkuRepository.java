package com.umc.linkyou.repository.LinkuRepository;

import com.umc.linkyou.domain.Linku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkuRepository extends JpaRepository<Linku, Long>, LinkuRepositoryCustom {
}
