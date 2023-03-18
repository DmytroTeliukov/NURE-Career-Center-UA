package com.example.nurecareercenterua.security.jwt.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.security.constants.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.example.nurecareercenterua.security.constants.SecurityConstant.*;
import static java.util.Arrays.stream;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public HttpHeaders generateJwtHeader(AccountDto account) {
        String jwt = generateJwtToken(account);
        HttpHeaders headers = new HttpHeaders();

        headers.add(SecurityConstant.JWT_TOKEN_HEADER_NAME, jwt);

        return headers;
    }

    public String generateJwtToken(AccountDto account) {
        return JWT.create()
                .withIssuer(TOKEN_ISSUE)
                .withAudience()
                .withIssuedAt(new Date())
                .withSubject(account.email())
                .withClaim(ROLE, account.role().name())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public Authentication getAuthentication(String email, GrantedAuthority authority, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public boolean isTokenValid(String email, String token) {
        JWTVerifier verifier = getVerifier();
        return StringUtils.isNotEmpty(email) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getVerifier();
        return verifier.verify(token).getSubject();
    }
    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public GrantedAuthority getAuthority(String token) {
        return new SimpleGrantedAuthority(getRoleFromToken(token));
    }

    private String getRoleFromToken(String token) {
        JWTVerifier verifier = getVerifier();
        return verifier.verify(token).getClaim(ROLE).asString();
    }

    private JWTVerifier getVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUE).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_NOT_VERIFIED_MESSAGE);
        }
        return verifier;
    }
}
