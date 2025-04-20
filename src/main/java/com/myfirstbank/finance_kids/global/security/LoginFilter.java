package com.myfirstbank.finance_kids.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myfirstbank.finance_kids.domain.user.record.CreateUserRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public static final long EXPIRED_MS = 60 * 60 * 10L;
    public static final String BEARER    = "Bearer";
    public static final String AUTHORIZATION = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateUserRequest loginRequest = objectMapper.readValue(request.getInputStream(), CreateUserRequest.class);

            String username = loginRequest.username();
            String password = loginRequest.password();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 JSON 파싱 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Long userId = customUserDetails.getUser().getId();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.createJwt(userId, EXPIRED_MS);

        response.addHeader(AUTHORIZATION, BEARER + " " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
