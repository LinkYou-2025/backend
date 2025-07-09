package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Folder;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "linku_folder")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LinkuFolder  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linku_folder_id")
    private Long linkuFolderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linku_id", nullable = false)
    private Linku linku;
}

