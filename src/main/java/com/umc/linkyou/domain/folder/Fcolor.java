package com.umc.linkyou.domain.folder;

import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.mapping.folder.UsersCategoryColor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fcolor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fcolor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fcolorId;

    @Column(length = 50, nullable = false)
    private String colorName;

    @Column(length = 20, nullable = false)
    private String colorCode1;

    @Column(length = 20, nullable = false)
    private String colorCode2;

    @Column(length = 20, nullable = false)
    private String colorCode3;

    @Column(length = 20, nullable = false)
    private String colorCode4;

    @OneToMany(mappedBy = "fcolor")
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "fcolor", cascade = CascadeType.ALL)
    private List<UsersCategoryColor> usersCategoryColorList = new ArrayList<>();
}
