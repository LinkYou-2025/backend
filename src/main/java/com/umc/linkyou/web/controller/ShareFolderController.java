package com.umc.linkyou.web.controller;

import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.folder.share.ShareFolderService;
import com.umc.linkyou.web.dto.folder.*;
import com.umc.linkyou.web.dto.folder.share.ShareFolderRequestDTO;
import com.umc.linkyou.web.dto.folder.share.ShareFolderResponseDTO;
import com.umc.linkyou.web.dto.folder.share.ViewerResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders/share")
@RequiredArgsConstructor
public class ShareFolderController {
    private final ShareFolderService shareFolderService;

    // 폴더 공유 (뷰어 권한 설정)
    @PostMapping("/{folderId}")
    @Operation(summary = "폴더 공유 (뷰어 권한 설정)")
    public ResponseEntity<ShareFolderResponseDTO> shareFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        ShareFolderRequestDTO request = new ShareFolderRequestDTO();
        request.setUserId(userDetails.getUsers().getId());
        request.setPermission("VIEWER");
        ShareFolderResponseDTO result = shareFolderService.shareFolder(
                userDetails.getUsers().getId(), folderId, request);
        return ResponseEntity.ok(result);
    }

    // 폴더 뷰어 조회
    @GetMapping("/{folderId}/members")
    @Operation(summary = "폴더 뷰어 조회")
    public ResponseEntity<List<ViewerResponseDTO>> getFolderViewers(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        List<ViewerResponseDTO> viewers = shareFolderService.getViewers(
                userDetails.getUsers().getId(), folderId);
        return ResponseEntity.ok(viewers);
    }

    // 뷰어 권한 수정
    @PutMapping("/{folderId}/members/{userFolderId}")
    @Operation(summary = "뷰어 권한 수정")
    public ResponseEntity<ShareFolderResponseDTO> updateViewerPermission(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId,
            @Valid @RequestBody FolderUpdateRequestDTO request
    ) {
        ShareFolderResponseDTO result = shareFolderService.updateViewerPermission(
                userDetails.getUsers().getId(), folderId, request);
        return ResponseEntity.ok(result);
    }
}