package com.umc.linkyou.service.folder;

import com.umc.linkyou.domain.Category;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import com.umc.linkyou.repository.CategoryRepository;
import com.umc.linkyou.repository.FolderRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.UsersFolderRepository.UsersFolderRepository;
import com.umc.linkyou.web.dto.folder.FolderCreateRequestDTO;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderTreeResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UsersFolderRepository usersFolderRepository;

    // 폴더 생성
    @Transactional
    public FolderResponseDTO createFolder(Long userId, FolderCreateRequestDTO req) {
        // 카테고리 조회
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
        // 부모 폴더 id 조회
        Folder parent = req.getParentFolderId() != null
                ? folderRepository.findById(req.getParentFolderId()).orElse(null)
                : null;

        // 폴더 테이블에 저장
        Folder folder = Folder.builder()
                .folderName(req.getFolderName())
                .category(category)
                .parentFolder(parent)
                .build();
        folderRepository.save(folder);

        // 유저폴더 매핑 테이블에 저장 및 소유자 true
        usersFolderRepository.save(
                UsersFolder.builder()
                        .user(userRepository.findById(userId).orElseThrow())
                        .folder(folder)
                        .isOwner(true)
                        .isBookmarked(false)
                        .isWriter(true)
                        .isViewer(false)
                        .build()
        );

        return FolderResponseDTO.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .parentFolderId(parent != null ? parent.getFolderId() : null)
                .build();
    }

    // 내 폴더 목록(트리) 조회
    // parentFolderID가 null인 중분류 폴더만 조회
    public List<FolderTreeResponseDTO> getMyFolderTree(Long userId) {
        List<Folder> topFolders = usersFolderRepository.searchFolders(
                userId,
                null,
                null,
                null,
                null,
                false
        );

        return topFolders.stream()
                .map(folder -> toTreeDto(folder, userId))
                .collect(Collectors.toList());
    }

    private FolderTreeResponseDTO toTreeDto(Folder folder, Long userId) {
        FolderTreeResponseDTO dto = new FolderTreeResponseDTO();
        dto.setFolderId(folder.getFolderId());
        dto.setFolderName(folder.getFolderName());
        dto.setCategoryId(folder.getCategory().getCategoryId());

        // 소분류 폴더면 하위 폴더 x
        if (folder.getParentFolder() != null) {
            dto.setChildren(new ArrayList<>());
        } else {
            // 부모 폴더로 자식 폴더 찾기
            List<Folder> children = usersFolderRepository.searchFolders(
                    userId,
                    null,
                    folder.getFolderId(),
                    null,
                    null,
                    false
            );

            dto.setChildren(
                    children.stream()
                            .map(child -> toTreeDto(child, userId))
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public List<FolderResponseDTO> getSubFolders(Long userId, Long parentFolderId) {
        List<Folder> subFolders = usersFolderRepository.searchFolders(
                userId,
                null,
                parentFolderId,
                null,
                null,
                false
        );

        return subFolders.stream()
                .map(folder -> FolderResponseDTO.builder()
                        .folderId(folder.getFolderId())
                        .folderName(folder.getFolderName())
                        .categoryId(folder.getCategory().getCategoryId())
                        .categoryName(folder.getCategory().getCategoryName())
                        .parentFolderId(parentFolderId)
                        .build())
                .collect(Collectors.toList());
    }
}
