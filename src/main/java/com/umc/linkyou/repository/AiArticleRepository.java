package com.umc.linkyou.repository;

import com.umc.linkyou.domain.AiArticle;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.mapping.UsersLinku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiArticleRepository extends JpaRepository<AiArticle, Long> {
   Optional<AiArticle> findByLinku(Linku linku);

    boolean existsByLinku_LinkuId(Long linkuId);
}
