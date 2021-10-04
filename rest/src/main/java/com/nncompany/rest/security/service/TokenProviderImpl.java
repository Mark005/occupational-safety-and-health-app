package com.nncompany.rest.security.service;

import com.nncompany.api.interfaces.IKeyGenerator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProviderImpl implements TokenProvider {

    @Value("${jwt.headerName}")
    private String authHeaderName;
    @Value("${jwt.expirationHours}")
    private Integer expirationHours;

    private Key key;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenProviderImpl(IKeyGenerator keyGenerator,
                             @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.key = keyGenerator.generateKey();
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void regenerateKey() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String getToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(new Date().getTime() + expirationHours * 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJwts = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJwts.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authHeaderName);
    }
}
