package com.umc.linkyou.domain.log;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.common.BaseEntity;
import com.umc.linkyou.domain.enums.CurationTopLogType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "curation_top_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CurationTopLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long curationTopEmotionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id", nullable = false)
    private Curation curation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurationTopLogType type;

    @Column(nullable = false)
    private Long refId;

    @Column(nullable = false)
    private int count;
}
