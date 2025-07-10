package com.umc.linkyou.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "domain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Domain {

    @Id
    @Column(name = "domain_id")
    private Long domainId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "domain_tail", length = 255, nullable = false)
    private String domainTail;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;
}
