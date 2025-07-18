package com.umc.linkyou.repository;

import com.umc.linkyou.domain.AiArticle;
import com.umc.linkyou.domain.mapping.UsersLinku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiArticleRepository extends JpaRepository<AiArticle, Long> {
    boolean existsByUsersLinku(UsersLinku usersLinku);
}
