package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Curation;
import com.umc.linkyou.domain.enums.CurationLinkuType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curation_linku")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CurationLinku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long curationLinkuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id", nullable = false)
    private Curation curation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_linku_id", nullable = false)
    private UsersLinku usersLinku;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurationLinkuType type;
}