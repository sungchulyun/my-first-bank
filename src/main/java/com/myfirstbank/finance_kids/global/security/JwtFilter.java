package com.myfirstbank.finance_kids.global.security;

import com.myfirstbank.finance_kids.global.config.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(isNonProtectedUrl(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(AUTHORIZATION);

        if(authorization == null || !authorization.startsWith(BEARER + " ")){
            logger.debug("Token is null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        if (token.trim().isEmpty()) {
            log.warn("JWT token is empty");
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isExpired(token)) {
            log.debug("Token is expired");
            log.debug("Extracted JWT token: '{}'", token);
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(token);

        CustomUserDetails userDetails = customUserDetailsService.loadUserById(id);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null
                , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean isNonProtectedUrl(HttpServletRequest request) {
        for(String urlPattern : SecurityConfig.PUBLIC_URLS){
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(urlPattern);

            if(matcher.matches(request)){
                return true;
            }
        }

        return false;
    }
}
