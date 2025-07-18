package com.umc.linkyou.domain.mapping.folder;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.common.BaseEntity;
import com.umc.linkyou.domain.folder.Folder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_folder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersFolder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_folder_id")
    private Long userFolderId;

    private Boolean isOwner;
    private Boolean isViewer;
    private Boolean isWriter;

    private Boolean isBookmarked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;
}
