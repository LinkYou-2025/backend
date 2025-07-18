package com.umc.linkyou.repository.mapping;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.mapping.CurationLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurationLikeRepository extends JpaRepository<CurationLike, Long> {

    boolean existsByUserAndCuration(Users user, Curation curation);

    Optional<CurationLike> findByUserAndCuration(Users user, Curation curation);

    List<CurationLike> findTop6ByUserOrderByCreatedAtDesc(Users user);

}

