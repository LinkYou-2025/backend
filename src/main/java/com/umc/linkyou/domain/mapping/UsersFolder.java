package com.umc.linkyou.domain.mapping;

import com.umc.linkyou.domain.Folder;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_folder")
public class UsersFolder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFolderId;

    // 사용자 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // 폴더 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    // 권한 설정
    @Column(nullable = false)
    private Boolean isOwner;

    @Column(nullable = false)
    private Boolean isViewer;

    @Column(nullable = false)
    private Boolean isWriter;

    @Column(nullable = false)
    private Boolean isBookmarked;
}
