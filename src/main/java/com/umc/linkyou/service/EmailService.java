package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.handler.UserHandler;
import com.umc.linkyou.converter.EmailConverter;
import com.umc.linkyou.domain.EmailVerification;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.repository.EmailRepository;
import com.umc.linkyou.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void sendEmail(String toEmail,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
            log.info("메일 전송 성공: {}", toEmail);
        } catch (RuntimeException e) {
            log.error("메일 전송 중 오류 발생 toEmail: {}, title: {}, text: {}", toEmail, title, text, e);
            throw new UserHandler(ErrorStatus._SEND_MAIL_FAILED);
        }
    }

    public void saveCode(String toEmail, String code) {
        Optional<EmailVerification> existing = emailRepository.findByEmail(toEmail);

        if (existing.isPresent()) {
            EmailVerification emailVerification = existing.get();
            emailVerification.setVerificationCode(code);
            emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(10));
            emailVerification.setIsVerified(false);
            emailRepository.save(emailVerification); // update
        } else {
            EmailVerification emailVerification = EmailConverter.toEmailVerification(toEmail, code, false);
            emailRepository.save(emailVerification); // insert
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    // 임시 비밀번호 저장
    public void savePassword(String toEmail, String password) {
        Optional<Users> optionalUser = userRepository.findByEmail(toEmail);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            String encodedPassword = passwordEncoder.encode(password);

            user.setPassword(encodedPassword);
            userRepository.save(user);
        } else {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}