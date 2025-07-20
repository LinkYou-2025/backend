package com.umc.linkyou.domain.classification;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.enums.Interest;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Interests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String interest;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 외래키 설정
    private Users user;

    @Column(nullable = false)
    private LocalDateTime selectedAt;

    public Interests() {

    }

    public Interests(String enumInterest, Users newUser) {
        this.interest = enumInterest;
        this.user = newUser;
        this.selectedAt = LocalDateTime.now();
        user.getInterests().add(this);
    }

    @PrePersist
    public void prePersist() {
        if (this.selectedAt == null) {
            this.selectedAt = LocalDateTime.now();
        }
    }

}
