package com.umc.linkyou.repository;

import com.umc.linkyou.domain.mapping.UsersLinku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersLinkuRepository  extends JpaRepository<UsersLinku, Long> {
    Optional<UsersLinku> findByUserIdAndLinku_Linku(Long userId, String url);
}
