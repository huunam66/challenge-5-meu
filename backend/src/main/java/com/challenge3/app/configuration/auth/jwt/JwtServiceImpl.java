package com.challenge3.app.configuration.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

//    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${spring.security.jwt.secret-key}")
    private String SECRET_KEY;

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

//    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getEncoded());
//    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String buildScope(Collection<? extends GrantedAuthority> grantedAuthorities){
        return grantedAuthorities
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
    }

    @Override
    public String extractToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String tokenExtracted = extractToken(token);
        return (tokenExtracted.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateToken(UserDetails userDetails) {

//        String jti = UUID.randomUUID().toString();

//        System.out.println(jti);

        Map<String, Object> claims = new HashMap<>();
//        claims.put("jti", jti);
        claims.put("scp", buildScope(userDetails.getAuthorities()));
        claims.put("sub", userDetails.getUsername());
        claims.put("iat", new Date());
        claims.put("exp", new Date(
                Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
        ));

        return Jwts
                .builder()
//                .setId(jti)
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS512
                ).compact();
    }
}

//try {
//        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
//        }
//                catch (MalformedJwtException | SignatureException e) {
//        return null;
//        }