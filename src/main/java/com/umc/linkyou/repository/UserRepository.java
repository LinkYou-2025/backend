package com.umc.linkyou.repository;

import com.umc.linkyou.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByNickName(String nickName);
    Optional<Users> findByEmail(String email);
<<<<<<< HEAD
=======
    Optional<Users> findById(Long id);
>>>>>>> ec6f03e64ead71dcad84aa4e11ecd4b46c35226e
}
