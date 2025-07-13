package com.umc.linkyou.domain;

import com.umc.linkyou.domain.enums.Purpose;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Purposes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Purpose purpose;

    @Column(nullable = false)
    private LocalDateTime selectedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 외래키 설정
    private Users user;

    public Purposes() {

    }

    public Purposes(Purpose enumPurpose, Users newUser) {
        this.purpose = enumPurpose;
        this.user = newUser;
        this.selectedAt = LocalDateTime.now();
        user.getPurposes().add(this);
    }

    @PrePersist
    public void prePersist() {
        System.out.println("[PREPERSIST] selectedAt = " + this.selectedAt);
        if (this.selectedAt == null) {
            this.selectedAt = LocalDateTime.now();
        }
    }

}
