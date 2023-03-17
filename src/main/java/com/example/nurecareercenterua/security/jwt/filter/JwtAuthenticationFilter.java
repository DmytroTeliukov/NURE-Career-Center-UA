package com.example.nurecareercenterua.security.jwt.filter;

import com.example.nurecareercenterua.security.constants.SecurityConstant;
import com.example.nurecareercenterua.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            String jwtToken = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstant.TOKEN_HEADER)) {
                jwtToken = authorizationHeader.substring(SecurityConstant.TOKEN_HEADER.length());
                String email = jwtTokenProvider.getSubject(jwtToken);
                if (jwtTokenProvider.isTokenValid(email, jwtToken) &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    GrantedAuthority authority = jwtTokenProvider.getAuthority(jwtToken);
                    Authentication authentication = jwtTokenProvider.getAuthentication(email, authority, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        filterChain.doFilter(request, response);
    }


}
