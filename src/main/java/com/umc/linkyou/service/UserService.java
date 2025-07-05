package com.umc.linkyou.service;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;

public interface UserService {

    Users joinUser(UserRequestDTO.JoinDTO request);
    UserResponseDTO.LoginResultDTO loginUser(UserRequestDTO.LoginRequestDTO request);
}
