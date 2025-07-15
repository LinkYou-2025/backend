package com.umc.linkyou.domain.classification;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.enums.Purpose;
import jakarta.persistence.*;

@Entity
public class Purposes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Purpose purpose;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 외래키 설정
    private Users user;

    public Purposes() {

    }

    public Purposes(Purpose enumPurpose, Users newUser) {
        this.purpose = enumPurpose;
        this.user = newUser;
        user.getPurposes().add(this);
    }

}
