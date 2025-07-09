package com.umc.linkyou.repository;

import com.umc.linkyou.domain.mapping.UsersLinku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersLinkuRepository  extends JpaRepository<UsersLinku, Long> {
}
