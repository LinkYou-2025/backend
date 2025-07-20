package com.umc.linkyou.repository.classification;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.Interests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interests, Long> {
    void deleteAllByUser(Users user);
}
