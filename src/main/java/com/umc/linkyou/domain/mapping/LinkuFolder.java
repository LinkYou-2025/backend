package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Folder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "linku_folder")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LinkuFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkuFolderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;
}

