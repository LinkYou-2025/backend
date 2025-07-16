package com.umc.linkyou.domain;

import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.classification.Situation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linku_id", nullable = false)
    private Linku linku;

    // FK: situation_id (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_id", nullable = false)
    private Situation situation;

    // FK: emotion_id (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "ai_feeling", length = 50)
    private String aiFeeling = "NEUTRAL"; // 기본 default 값

    @Column(name = "ai_category", length = 50)
    private String aiCategory = "ETC"; // 기본 default 값

    @Column(name = "summary", length = 255, nullable = false)
    private String summary;

    @Column(name = "img_url", columnDefinition = "text")
    private String imageUrl;

    @Column(name = "keyword", length = 200)
    private String keyword;
}
