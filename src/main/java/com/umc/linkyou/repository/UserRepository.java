package com.umc.linkyou.repository;

import com.umc.linkyou.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByNickName(String nickName);
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long id);
    void deleteAllById(Long userId);
}
