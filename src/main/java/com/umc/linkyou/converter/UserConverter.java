package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.enums.Gender;
import com.umc.linkyou.domain.enums.Job;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class UserConverter {
    public static Users toUser(UserRequestDTO.JoinDTO request){
        Gender gender = null;
        switch(request.getGender()){
            case 1: gender = Gender.MALE; break;
            case 2: gender = Gender.FEMALE; break;
            //case 3: gender = Gender.NONE; break;
        }

        Job job = null;
        switch(request.getJob()){
            case 1: job = Job.HIGH_SCHOOL_STUDENT; break;
            case 2: job = Job.UNIVERSITY_STUDENT; break;
            case 3: job = Job.OFFICE_STUDENT; break;
            case 4: job = Job.SELF_EMPLOYED; break;
            case 5: job = Job.FREELANCER; break;
            case 6: job = Job.JOB_SEEKER; break;
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

}
