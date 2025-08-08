package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Alarm;
import com.umc.linkyou.domain.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_alarm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_alarm_id", nullable = false)
    private Alarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
