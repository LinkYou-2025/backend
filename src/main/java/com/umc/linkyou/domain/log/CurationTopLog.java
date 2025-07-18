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
    @Column(name = "curation_top_log_id", nullable = false)
    private Long curationTopLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id", nullable = false)
    private Curation curation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CurationTopLogType type;

    @Column(name = "ref_id", nullable = false)
    private Long refId;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "tag_name", length = 30, nullable = false)
    private String tagName;
}
