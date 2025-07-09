package com.umc.linkyou.domain;

import com.umc.linkyou.domain.common.BaseEntity;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "linku")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Linku extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linku_id")
    private Long linkuId;

    // category_id FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // emotion_id FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    // domain_id FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    @Column(name = "linku", columnDefinition = "text", nullable = false)
    private String linku;

    @Column(name = "memo", length = 200)
    private String memo;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;
}
