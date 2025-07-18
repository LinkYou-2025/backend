package com.umc.linkyou.domain.log;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.common.BaseEntity;
import com.umc.linkyou.domain.mapping.SituationJob;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "situation_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SituationLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long situationLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_job_id", nullable = false)
    private SituationJob situationJob;
}

