package com.umc.linkyou.service.folder.share;

import com.umc.linkyou.domain.enums.PermissionType;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import com.umc.linkyou.repository.FolderRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.usersFolderRepository.UsersFolderRepository;
import com.umc.linkyou.web.dto.folder.share.FolderPermissionRequestDTO;
import com.umc.linkyou.web.dto.folder.share.ShareFolderRequestDTO;
import com.umc.linkyou.web.dto.folder.share.ShareFolderResponseDTO;
import com.umc.linkyou.web.dto.folder.share.ViewerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareFolderServiceImpl implements ShareFolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final UsersFolderRepository usersFolderRepository;

    // 폴더 공유 (뷰어 권한 설정)
    public ShareFolderResponseDTO shareFolder(Long userId, Long folderId, ShareFolderRequestDTO request) {
        // 이미 뷰어 권한 갖고 있는지 검사
        UsersFolder usersFolder = usersFolderRepository
                .findByUserIdAndFolderId(request.getUserId(), folderId)
                .orElseGet(() -> UsersFolder.builder()
                        .user(userRepository.getById(request.getUserId()))
                        .folder(folderRepository.getById(folderId))
                        .build()
                );

        usersFolder.setIsViewer(true);
        usersFolder.setIsWriter(true);
        usersFolder.setIsOwner(false);
        usersFolder.setIsBookmarked(false);

        usersFolderRepository.save(usersFolder);

        return ShareFolderResponseDTO.builder()
                .folderId(folderId)
                .userId(request.getUserId())
                .permission("VIEWER")
                .sharedAt(usersFolder.getCreatedAt().toString())
                .build();
    }

    // 폴더 뷰어 조회
    public List<ViewerResponseDTO> getViewers(Long userId, Long folderId) {
        List<UsersFolder> viewers = usersFolderRepository.findByFolderFolderIdAndIsViewerTrue(folderId);

        return viewers.stream()
                .map(uf -> {
                    ViewerResponseDTO dto = new ViewerResponseDTO();
                    dto.setUserId(uf.getUser().getId());
                    dto.setUserName(uf.getUser().getNickName());
                    dto.setPermission("VIEWER");
                    return dto;
                })
                .toList();

    }

    // 유저의 폴더 권한 수정
    public ShareFolderResponseDTO updateViewerPermission(Long userId, Long folderId, Long userFolderId, FolderPermissionRequestDTO request) {
        UsersFolder usersFolder = usersFolderRepository.findById(userFolderId).orElseThrow(() -> new IllegalArgumentException("해당 유저의 폴더 권한 정보가 존재하지 않습니다."));

        // 오너일 경우 권한 변경 불가
        if (Boolean.TRUE.equals(usersFolder.getIsOwner())) {
            throw new IllegalStateException("폴더 주인 권한 수정 불가");
        }

        PermissionType permission = request.getPermission();

        if (permission == PermissionType.VIEWER) {
            usersFolder.setIsWriter(false);
            usersFolder.setIsViewer(true);
        } else if (permission == PermissionType.WRITER) {
            usersFolder.setIsWriter(true);
            usersFolder.setIsViewer(true);
        } else if (permission == PermissionType.NONE){
            usersFolder.setIsWriter(false);
            usersFolder.setIsViewer(false);
        }
        usersFolderRepository.save(usersFolder);

        return ShareFolderResponseDTO.builder()
                .folderId(folderId)
                .userId(usersFolder.getUser().getId())
                .permission(permission.name())
                .sharedAt(usersFolder.getUpdatedAt().toString())
                .build();
    }
}

