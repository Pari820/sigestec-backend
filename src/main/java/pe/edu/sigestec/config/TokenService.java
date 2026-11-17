package pe.edu.sigestec.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TokenService {
    private final SecretKey key;

    public TokenService(@Value("${app.jwt.secret:}") String configured) {
        byte[] bytes = (configured == null || configured.isBlank()) ? new byte[48] : Decoders.BASE64.decode(configured);
        if (configured == null || configured.isBlank()) new java.security.SecureRandom().nextBytes(bytes);
        if (bytes.length < 48)
            throw new IllegalArgumentException("JWT_SECRET_BASE64 debe contener al menos 48 bytes aleatorios");
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String create(String username) {
        Instant now = Instant.now();
        return Jwts.builder().subject(username).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(7200))).signWith(key, Jwts.SIG.HS384).compact();
    }

    public String subject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
