package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.SuccessStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import com.umc.linkyou.service.folder.share.ShareFolderService;
import com.umc.linkyou.web.dto.folder.share.FolderPermissionRequestDTO;
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
    public ApiResponse<ShareFolderResponseDTO> shareFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        ShareFolderRequestDTO request = new ShareFolderRequestDTO();
        request.setUserId(userDetails.getUsers().getId());
        request.setPermission("VIEWER");
        ShareFolderResponseDTO response = shareFolderService.shareFolder(
                userDetails.getUsers().getId(), folderId, request);
        return ApiResponse.of(SuccessStatus._FOLDER_SHARE_OK, response);
    }

    // 폴더 뷰어 조회
    @GetMapping("/{folderId}/members")
    @Operation(summary = "폴더 뷰어 조회")
    public ApiResponse<List<ViewerResponseDTO>> getFolderViewers(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        List<ViewerResponseDTO> viewers = shareFolderService.getViewers(
                userDetails.getUsers().getId(), folderId);
        return ApiResponse.of(SuccessStatus._FOLDER_MEMBERS_OK, viewers);
    }

    // 뷰어, 라이터 권한 수정
    @PutMapping("/{folderId}/members/{userFolderId}")
    @Operation(summary = "폴더 권한 수정")
    public ApiResponse<ShareFolderResponseDTO> updateViewerPermission(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId,
            @PathVariable Long userFolderId,
            @Valid @RequestBody FolderPermissionRequestDTO request
    ) {
        ShareFolderResponseDTO response = shareFolderService.updateViewerPermission(
                userDetails.getUsers().getId(), folderId, userFolderId, request);
        return ApiResponse.of(SuccessStatus._FOLDER_PERMISSION_OK, response);
    }

    // 폴더 비공개 전환
    @PostMapping("/{folderId}/unshare")
    @Operation(summary = "폴더 비공개 전환")
    public ApiResponse<ShareFolderResponseDTO> unshareFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long folderId
    ) {
        // 모든 유저의 (폴더 주인 제외) 뷰어, writer 권한 false
        ShareFolderResponseDTO response = shareFolderService.unshare(userDetails.getUsers().getId(), folderId);
        return ApiResponse.of(SuccessStatus._FOLDER_UNSHARE_OK, response);
    }
}