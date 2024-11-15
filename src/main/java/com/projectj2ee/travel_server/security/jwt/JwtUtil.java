package com.projectj2ee.travel_server.security.jwt;

import com.projectj2ee.travel_server.repository.InvalidatedRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.lifespan}")
    private long tokenLifeSpan;

    @Autowired
    private final InvalidatedRepository invalidatedRepository;

    public JwtUtil(InvalidatedRepository invalidatedRepository) {
        this.invalidatedRepository = invalidatedRepository;
    }


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String,Object> claims,String username){
        return Jwts.builder().addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+tokenLifeSpan))
                .id(UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .compact();

    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsernameFromToken(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsernameFromToken(token);
        final String id = extractClaim(token,Claims::getId);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !invalidatedRepository.existsById(id);
    }

}
