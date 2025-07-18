package com.umc.linkyou.repository.classification;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.Purposes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurposeRepository extends JpaRepository<Purposes, Long> {
    void deleteAllByUser(Users user);
}
