package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.classification.Job;
import com.umc.linkyou.domain.enums.Gender;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class UserConverter {
    public static Users toUser(UserRequestDTO.JoinDTO request, Job job){
        Gender gender = null;
        switch(request.getGender()){
            case 1: gender = Gender.MALE; break;
            case 2: gender = Gender.FEMALE; break;
            //case 3: gender = Gender.NONE; break;
        }

        return new Users().builder()
                .nickName(request.getNickName())
                .email(request.getEmail())
                .password(request.getPassword())
                .gender(gender)
                .job(job)
                .build();
    }

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(Users users){
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(users.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toLoginResultDTO(Long userId, String accessToken) {

        return new UserResponseDTO.LoginResultDTO().builder()
                .userId(userId)
                .accessToken(accessToken)
                .build();
    }

    public static UserResponseDTO.UserInfoDTO toUserInfoDTO(String nickName, Long linkCount, Long folderCount, Long aiLinkCount) {
        return UserResponseDTO.UserInfoDTO.builder()
                .nickname(nickName)
                .myLinku(linkCount)
                .myFolder(folderCount)
                .myAiLinku(aiLinkCount)
                .build();
    }
}
