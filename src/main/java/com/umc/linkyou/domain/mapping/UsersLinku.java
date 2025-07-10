package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_linku")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UsersLinku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_linku_id")
    private Long userLinkuId;

    // 연관관계: Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users user;

    // 연관관계: Linku
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linku_id", nullable = false)
    private Linku linku;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    //== 연관관계 편의 메서드 등 필요시 추가 ==//
}
