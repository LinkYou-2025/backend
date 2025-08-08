package com.umc.linkyou.service.folder.shared;

import com.umc.linkyou.converter.FolderConverter;
import com.umc.linkyou.domain.enums.PermissionType;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import com.umc.linkyou.repository.FolderRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.usersFolderRepository.UsersFolderRepository;
import com.umc.linkyou.service.folder.share.ShareFolderService;
import com.umc.linkyou.web.dto.folder.FolderListResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;
import com.umc.linkyou.web.dto.folder.share.FolderPermissionRequestDTO;
import com.umc.linkyou.web.dto.folder.share.ShareFolderRequestDTO;
import com.umc.linkyou.web.dto.folder.share.ShareFolderResponseDTO;
import com.umc.linkyou.web.dto.folder.share.ViewerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SharedFolderServiceImpl implements SharedFolderService {
    private final UsersFolderRepository usersFolderRepository;
    private final FolderConverter folderConverter;

    // 공유 받은 폴더 목록 조회
    public List<FolderListResponseDTO> getSharedFolders(Long userId) {
        // 유저 폴더 테이블에서 isOwner가 false고 isViewer가 true인 폴더들 조회
        List<Folder> folders = usersFolderRepository.findSharedFolders(userId);

        return folders.stream()
                .map(folder -> FolderListResponseDTO.builder()
                        .folderId(folder.getFolderId())
                        .folderName(folder.getFolderName())
                        .build())
                .collect(Collectors.toList());
    }

    // 공유 받은 폴더 삭제
    public FolderResponseDTO deleteSharedFolder(Long userId, Long folderId) {
        // 폴더 조회
        UsersFolder usersFolder = usersFolderRepository
                .findByUserIdAndFolderId(userId, folderId)
                .orElseThrow(() -> new AccessDeniedException("공유 폴더가 없습니다."));

        // 유저 폴더 테이블에서 삭제
        usersFolderRepository.delete(usersFolder);

        Folder folder = usersFolder.getFolder();
        return folderConverter.toFolderResponseDTO(folder);
    }
}

