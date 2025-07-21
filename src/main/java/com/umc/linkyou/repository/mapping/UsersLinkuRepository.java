package com.umc.linkyou.repository.mapping;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.mapping.UsersLinku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersLinkuRepository  extends JpaRepository<UsersLinku, Long> {
    Optional<UsersLinku> findByUserIdAndLinku_Linku(Long userId, String url);

    Optional<UsersLinku> findByUserAndLinku(Users user, Linku linku);
}
