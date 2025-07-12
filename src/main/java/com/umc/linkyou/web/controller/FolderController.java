package com.umc.linkyou.web.controller;

import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.folder.FolderService;
import com.umc.linkyou.web.dto.folder.FolderCreateRequestDTO;
import com.umc.linkyou.web.dto.folder.FolderResponseDTO;
import com.umc.linkyou.web.dto.folder.FolderTreeResponseDTO;
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
            @RequestBody FolderCreateRequestDTO request
    ) {
        FolderResponseDTO response = folderService.createFolder(userDetails.getUsers().getId(), request);
        return ResponseEntity.ok(response);
    }

    // 내 폴더 목록(트리) 조회
    @GetMapping("/my")
    @Operation(summary = "내 폴더 목록(트리) 조회")
    public ResponseEntity<List<FolderTreeResponseDTO>> getMyFolderTree(
            @RequestParam Long userId
    ) {
        List<FolderTreeResponseDTO> folderTree = folderService.getMyFolderTree(userId);
        return ResponseEntity.ok(folderTree);
    }
}
