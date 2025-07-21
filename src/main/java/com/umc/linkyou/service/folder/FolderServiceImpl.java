package com.umc.linkyou.service.folder;

import com.umc.linkyou.converter.FolderConverter;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import com.umc.linkyou.repository.FolderRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.UsersFolderRepository.UsersFolderRepository;
import com.umc.linkyou.web.dto.folder.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final UsersFolderRepository usersFolderRepository;
    private final FolderConverter folderConverter;

    // 폴더 생성
    @Transactional
    public FolderResponseDTO createFolder(Long userId, Long parentFolderId, FolderCreateRequestDTO req) {
        // 부모 폴더 조회
        Folder parent = folderRepository.findById(parentFolderId).orElse(null);

        // 부모 카테고리 조회
        if (parent == null) {
            throw new IllegalArgumentException("부모 폴더가 없습니다.");
        }
        Category category = parent.getCategory();
        if (category == null) {
            throw new IllegalArgumentException("카테고리가 없습니다.");
        }

        // 폴더 테이블에 저장
        Folder folder = Folder.builder()
                .folderName(req.getFolderName())
                .category(category)
                .parentFolder(parent).build();
        folderRepository.save(folder);

        // 유저폴더 매핑 테이블에 저장 및 소유자 true
        usersFolderRepository.save(UsersFolder.builder()
                .user(userRepository
                        .findById(userId)
                        .orElseThrow())
                .folder(folder)
                .isOwner(true)
                .isBookmarked(false)
                .isWriter(true)
                .isViewer(false)
                .build());

        return FolderResponseDTO.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .parentFolderId(parent.getFolderId()).build();
    }

    // 폴더 이름 수정
    @Transactional
    public FolderResponseDTO updateFolder(Long userId, Long folderId, FolderUpdateRequestDTO req) {
        // 폴더 조회
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new IllegalArgumentException("폴더 없음"));

        // 주인 여부 확인
        boolean isOwner = usersFolderRepository.existsFolderOwner(userId, folderId);
        if (!isOwner) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        if (req.getFolderName() != null) folder.setFolderName(req.getFolderName());
        folder.setUpdatedAt(LocalDateTime.now());

        return folderConverter.toFolderResponseDTO(folder);
    }

    // 폴더 삭제
    @Transactional
    public FolderResponseDTO deleteFolder(Long userId, Long folderId) {
        // 폴더 조회
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new IllegalArgumentException("폴더 없음"));

        // 주인 여부 확인
        boolean isOwner = usersFolderRepository.existsFolderOwner(userId, folderId);
        if (!isOwner) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        folderRepository.delete(folder);

        return folderConverter.toFolderResponseDTO(folder);
    }

    // 내 폴더 목록(트리) 조회
    public List<FolderTreeResponseDTO> getMyFolderTree(Long userId) {
        // 유저 모든 폴더 조회
        List<Folder> allFolders = usersFolderRepository.searchFolders(userId, null, null, null, null, false);

        // 트리 구성, key = 중분류 폴더 ID, value = 하위 폴더
        Map<Long, List<Folder>> parentChildMap = allFolders.stream()
                .collect(Collectors.groupingBy(folder ->
                        folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : 0L));

        // 중분류부터 재귀적로 트리 구성
        return parentChildMap.getOrDefault(0L, new ArrayList<>()).stream()
                .map(folder -> buildTreeFromMap(folder, parentChildMap))
                .collect(Collectors.toList());
    }

    private FolderTreeResponseDTO buildTreeFromMap(Folder folder, Map<Long, List<Folder>> parentChildMap) {
        FolderTreeResponseDTO dto = folderConverter.toFolderTreeDTO(folder);

        List<Folder> childFolders = parentChildMap.get(folder.getFolderId());
        if (childFolders != null && !childFolders.isEmpty()) {
            List<FolderTreeResponseDTO> childDTOs = childFolders.stream()
                    .map(child -> buildTreeFromMap(child, parentChildMap))
                    .collect(Collectors.toList());
            dto.setChildren(childDTOs);
        } else {
            dto.setChildren(null);
        }
        return dto;
    }

    // 자식 폴더 목록 조회
    public List<FolderListResponseDTO> getSubFolders(Long userId, Long parentFolderId) {
        List<Folder> subFolders = usersFolderRepository.searchFolders(userId, null, parentFolderId, null, null, false);

        return subFolders.stream()
                .map(folder -> FolderListResponseDTO.builder()
                        .folderId(folder.getFolderId())
                        .folderName(folder.getFolderName())
                        .parentFolderId(parentFolderId)
                        .build())
                .collect(Collectors.toList());
    }

    // 북마크 설정/해제
    @Transactional
    public FolderResponseDTO updateBookmark(Long userId, Long folderId, Boolean isBookmarked) {
        UsersFolder usersFolder = usersFolderRepository.findByUserIdAndFolderId(userId, folderId);

        usersFolder.setIsBookmarked(isBookmarked);

        return folderConverter.toFolderResponseDTO(usersFolder.getFolder());
    }
}
