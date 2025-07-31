package com.umc.linkyou.web.controller;

import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.folder.FolderService;
import com.umc.linkyou.web.dto.folder.*;
import com.umc.linkyou.web.dto.folder.linku.FolderLinkusResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    // (소분류) 폴더 생성
    @PostMapping("/{parentFolderId}/subfolders")
    @Operation(summary = "소분류 폴더 생성")
    public ResponseEntity<FolderResponseDTO> createFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long parentFolderId,
            @RequestBody FolderCreateRequestDTO request
    ) {
        FolderResponseDTO response = folderService.createFolder(userDetails.getUsers().getId(), parentFolderId, request);
        return ResponseEntity.ok(response);
    }

    // (소분류) 폴더 수정
    @PutMapping("/subfolders/{folderId}")
    @Operation(summary = "소분류 폴더 수정")
    public ResponseEntity<FolderResponseDTO> updateFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId,
            @RequestBody FolderUpdateRequestDTO request
    ) {
        FolderResponseDTO response = folderService.updateFolder(userDetails.getUsers().getId(), folderId, request);
        return ResponseEntity.ok(response);
    }

    // (소분류) 폴더 삭제
    @DeleteMapping("/subfolders/{folderId}")
    @Operation(summary = "소분류 폴더 삭제")
    public ResponseEntity<FolderResponseDTO> deleteFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        FolderResponseDTO response = folderService.deleteFolder(userDetails.getUsers().getId(), folderId);
        return ResponseEntity.ok(response);
    }

    // 내 폴더 목록(트리) 조회
    @GetMapping("/my")
    @Operation(summary = "내 폴더 목록(트리) 조회")
    public ResponseEntity<List<FolderTreeResponseDTO>> getMyFolderTree(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<FolderTreeResponseDTO> folderTree = folderService.getMyFolderTree(userDetails.getUsers().getId());
        return ResponseEntity.ok(folderTree);
    }

    // 중분류 폴더 목록 조회
    @GetMapping("/parentFolders")
    @Operation(summary = "중분류 폴더 조회")
    public ResponseEntity<List<FolderListResponseDTO>> getParentFolderList(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<FolderListResponseDTO> folderList = folderService.getParentFolders(userDetails.getUsers().getId());
        return ResponseEntity.ok(folderList);
    }

    // (중분류) 하위 폴더 목록 조회
    @GetMapping("/{parentFolderId}/subfolders")
    @Operation(summary = "중분류 내부의 하위 폴더 조회")
    public ResponseEntity<List<FolderListResponseDTO>> getSubFolderList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long parentFolderId
    ) {
        List<FolderListResponseDTO> folderList = folderService.getSubFolders(userDetails.getUsers().getId(), parentFolderId);
        return ResponseEntity.ok(folderList);
    }

    // 북마크 설정/해제
    @PutMapping("/{folderId}/bookmark")
    @Operation(summary = "북마크 설정/해제")
    public ResponseEntity<FolderResponseDTO> updateBookmark(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId,
            @RequestBody BookmarkUpdateRequestDTO request
    ) {
        FolderResponseDTO response = folderService.updateBookmark(
                userDetails.getUsers().getId(), folderId, request.getIsBookmarked()
        );
        return ResponseEntity.ok(response);
    }

    // 폴더 내부 링크, 폴더 목록 조회
    @GetMapping("/{folderId}/linkus")
    @Operation(summary = "폴더 내부 링크, 폴더 목록 조회")
    public ResponseEntity<FolderLinkusResponseDTO> getFolderLinkus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String cursor
    ) {
        FolderLinkusResponseDTO response = folderService.getFolderLinkus(
                userDetails.getUsers().getId(), folderId, limit, cursor);
        return ResponseEntity.ok(response);
    }
}
