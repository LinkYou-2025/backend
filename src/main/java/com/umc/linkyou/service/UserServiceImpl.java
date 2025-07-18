package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.GeneralException;
import com.umc.linkyou.apiPayload.exception.handler.UserHandler;
import com.umc.linkyou.config.security.jwt.JwtTokenProvider;
import com.umc.linkyou.converter.UserConverter;
import com.umc.linkyou.domain.EmailVerification;
import com.umc.linkyou.domain.classification.Interests;
import com.umc.linkyou.domain.classification.Job;
import com.umc.linkyou.domain.classification.Purposes;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.domain.enums.Interest;
import com.umc.linkyou.domain.enums.Purpose;
import com.umc.linkyou.repository.EmailRepository;
import com.umc.linkyou.repository.UserQueryRepository;
import com.umc.linkyou.repository.UserRepository;
import com.umc.linkyou.repository.classification.InterestRepository;
import com.umc.linkyou.repository.classification.JobRepository;
import com.umc.linkyou.repository.classification.PurposeRepository;
import com.umc.linkyou.web.dto.EmailVerificationResponse;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;
//import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final EmailService emailService;

    private final EmailRepository emailRepository;

    private final JobRepository jobRepository;

    private final UserQueryRepository userQueryRepository;

    private final InterestRepository interestRepository;

    private final PurposeRepository purposeRepository;

    @Value("${auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Override
    @Transactional
    public Users joinUser(UserRequestDTO.JoinDTO request){
        if (userRepository.findByNickName(request.getNickName()).isPresent()) {
            throw new UserHandler(ErrorStatus._DUPLICATE_NICKNAME);
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserHandler(ErrorStatus._DUPLICATE_JOIN_REQUEST);
        }
        // Job 엔티티를 DB에서 조회
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));

        Users newUser = UserConverter.toUser(request,job);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<String> purposeNames = request.getPurposeList(); // 프론트에서 받은 enum 이름 리스트

        List<Purposes> purposeList = purposeNames.stream()
                .map(name -> {
                    String purpose = name;
                    return new Purposes(purpose, newUser);
                })
                .toList();

        List<String> interestNames = request.getInterestList(); // 프론트에서 받은 enum 이름 리스트

        List<Interests> interestList = interestNames.stream()
                .map(name -> {
                    String interest = name; // 문자열 → enum
                    return new Interests(interest, newUser);
                })
                .toList();

        newUser.setPurposes(purposeList);
        newUser.setInterests(interestList);

        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDTO.LoginResultDTO loginUser(UserRequestDTO.LoginRequestDTO request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserHandler(ErrorStatus._USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus._INVALID_PASSWORD);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                Collections.singleton(() -> user.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);

        return UserConverter.toLoginResultDTO(
                user.getId(),
                accessToken
        );
    }

    @Override
    public void validateNickNameNotDuplicate(String nickname) {
        if (userRepository.findByNickName(nickname).isPresent()) {
            throw new UserHandler(ErrorStatus._DUPLICATE_NICKNAME);
        }
    }

    // 이메일 인증
    // 인증 코드 전송
    public void sendCode(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "Travel with me 이메일 인증 번호";
        String authCode = this.createCode();

        log.info("인증 코드: {}", authCode);

        try {
            emailService.sendEmail(toEmail, title, authCode);
            emailService.saveCode(toEmail, authCode);
            //redisService.setValues(AUTH_CODE_PREFIX + toEmail,
            //        authCode, Duration.ofMillis(this.authCodeExpirationMillis));
            log.info("이메일 전송 완료: {}", toEmail);
        } catch (Exception e) {
            log.error("이메일 전송 실패: {}", toEmail, e);
            throw e; // 혹은 적절한 커스텀 예외를 던짐
        }
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new UserHandler(ErrorStatus._DUPLICATE_JOIN_REQUEST);
        }
    }

    // 인증 코드 생성
    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new UserHandler(ErrorStatus._NO_SUCH_ALGORITHM);
        }
    }

    // 인증 코드 검증
    public EmailVerificationResponse verifyCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        EmailVerification verification = emailRepository.findByEmail(email)
                .orElseThrow(() -> new UserHandler(ErrorStatus._VERIFICATION_FAILED));

        // 만료 시간 체크
        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UserHandler(ErrorStatus._EXPIRED_VERIFICATION_CODE);
        }

        // 코드 일치 여부 확인
        boolean isMatch = verification.getVerificationCode().equals(authCode);

        // 결과 반영
        if (isMatch) {
            verification.setIsVerified(true);
            emailRepository.save(verification);
        }

        return EmailVerificationResponse.of(isMatch);
    }

    // 마이페이지 조회
    @Override
    public UserResponseDTO.UserInfoDTO userInfo(Long userId){
        String nickName = userQueryRepository.findNicknameByUserId(userId);
        Long linkCount = userQueryRepository.countLinksByUserId(userId);
        Long folderCount = userQueryRepository.countFoldersByUserId(userId);
        Long aiLinkCount = userQueryRepository.countAiLinksByUserId(userId);

        return UserConverter.toUserInfoDTO(
                nickName, linkCount, folderCount, aiLinkCount
        );
    }

    // 마이페이지 수정
    @Override
    @Transactional
    public void updateUserProfile(Long userId, UserRequestDTO.UpdateProfileDTO request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus._USER_NOT_FOUND));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));
        user.setJob(job);

        // 닉네임 업데이트
        if (request.getNickname() != null) {
            user.setNickName(request.getNickname());
        }

        purposeRepository.deleteAllByUser(user); // 기존 목적 삭제

        List<Purposes> newPurposes = request.getPurposes().stream()
                .map(purpose -> new Purposes(purpose, user))
                .collect(Collectors.toList());
        purposeRepository.saveAll(newPurposes);

        interestRepository.deleteAllByUser(user); // 기존 관심사 삭제

        List<Interests> newInterests = request.getInterests().stream()
                .map(interest -> new Interests(interest, user))
                .collect(Collectors.toList());
        interestRepository.saveAll(newInterests);

        userRepository.save(user);
    }
}

