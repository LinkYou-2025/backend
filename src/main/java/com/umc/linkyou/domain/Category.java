package com.umc.linkyou.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    private Long categoryId;

    @Column(length = 20, nullable = false)
    private String colorCode;

    @Column(length = 100)
    private String name;
}
