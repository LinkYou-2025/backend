package com.umc.linkyou.domain;

import com.umc.linkyou.domain.enums.Emotion;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "linku")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Linku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linku_folder_id", nullable = false)
    private LinkuFolder linkuFolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String linku;

    @Column(length = 200)
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Emotion emotion; // enum 정의 필요

    @Column(length = 200)
    private String domain;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

