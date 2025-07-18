package com.umc.linkyou.domain;

import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.classification.Situation;
import com.umc.linkyou.domain.common.BaseEntity;
import com.umc.linkyou.domain.mapping.UsersLinku;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_id", nullable = false)
    private Situation situation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linku_id", nullable = false, unique = true)
    private Linku linku;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "ai_feeling_id")
    private Long aiFeelingId;

    @Column(name = "ai_category_id")
    private Long aiCategoryId;

    @Column(name = "summary", nullable = false, length = 255)
    private String summary;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "keyword", columnDefinition = "TEXT")
    private String keyword;
}
