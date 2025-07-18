package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "linku_folder")
public class LinkuFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkuFolderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_linku_id", nullable = false)
    private UsersLinku usersLinku;

}


