package com.umc.linkyou.service.folder.share;

import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import com.umc.linkyou.repository.FolderRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.UsersFolderRepository.UsersFolderRepository;
import com.umc.linkyou.service.folder.FolderService;
import com.umc.linkyou.web.dto.folder.FolderUpdateRequestDTO;
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
        List<UsersFolder> viewers = usersFolderRepository.findByFolderIdAndIsViewerTrue(folderId);

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

    // 특정 뷰어 권한 수정
    public ShareFolderResponseDTO updateViewerPermission(Long userId, Long folderId, FolderUpdateRequestDTO request) {
        // userId는 권한을 수정할 대상 유저의 id
        UsersFolder usersFolder = usersFolderRepository.findByUserIdAndFolderId(userId, folderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 폴더 권한 정보가 존재하지 않습니다."));

        String permission = request.getPermission();
        if ("OWNER".equalsIgnoreCase(permission)) {
            usersFolder.setIsOwner(true);
            usersFolder.setIsWriter(true);
            usersFolder.setIsViewer(true);
        } else if ("WRITER".equalsIgnoreCase(permission)) {
            usersFolder.setIsOwner(false);
            usersFolder.setIsWriter(true);
            usersFolder.setIsViewer(true);
        } else if ("VIEWER".equalsIgnoreCase(permission)) {
            usersFolder.setIsOwner(false);
            usersFolder.setIsWriter(false);
            usersFolder.setIsViewer(true);
        }

        usersFolderRepository.save(usersFolder);

        return ShareFolderResponseDTO.builder()
                .folderId(folderId)
                .userId(userId)
                .permission(permission)
                .sharedAt(usersFolder.getCreatedAt().toString())
                .build();
    }
}

