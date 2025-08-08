package com.umc.linkyou.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.umc.linkyou.config.properties.Constants;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Order(0)
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = resolveToken(request);

            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            log.info("AccessToken 만료됨, RefreshToken으로 재발급 시도");
            reissueAccessToken(request, response, e);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
            filterChain.doFilter(request, response);
    }


    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception originalException) {
        try {
            String oldAccessToken = resolveToken(request);
            if (oldAccessToken != null && oldAccessToken.startsWith(Constants.TOKEN_PREFIX)) {
                oldAccessToken = oldAccessToken.substring(Constants.TOKEN_PREFIX.length());
            }

            // Refresh Token은 Refresh-Token 헤더에서 직접 꺼냄
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken == null || oldAccessToken == null) {
                throw originalException;
            }
            // Bearer 접두사 제거
            if (refreshToken.startsWith(Constants.TOKEN_PREFIX)) {
                refreshToken = refreshToken.substring(Constants.TOKEN_PREFIX.length());
            }
            jwtTokenProvider.validateRefreshToken(refreshToken, oldAccessToken);
            String newAccessToken = jwtTokenProvider.recreateAccessToken(oldAccessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 새 AccessToken을 헤더로 응답에 추가
            log.info("새로운 accesstoken 발급, 헤더에 추가");
            response.setHeader("New-Access-Token", newAccessToken);

        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTH_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_PREFIX)) {
            return bearerToken.substring(Constants.TOKEN_PREFIX.length());
        }
        return null;
    }
}

