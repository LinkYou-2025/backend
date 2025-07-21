package com.umc.linkyou.domain.mapping.folder;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.folder.Fcolor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_category_color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersCategoryColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_category_color_id")
    private Long userCategoryColorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_code", nullable = false)
    private Fcolor fcolor;
}
