package com.umc.linkyou.web.controller;

import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.folder.FolderService;
import com.umc.linkyou.web.dto.folder.*;
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
}
