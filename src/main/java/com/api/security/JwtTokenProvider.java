package com.api.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.exception.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;
    
    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
    
    public String generateToken(Authentication authentication) {
    	String username = authentication.getName();
    	Date currentDate = new Date();
    	Date expireDate  = new Date(currentDate.getTime() + jwtExpirationDate);
    	
    	return Jwts.builder()
    			.setSubject(username)
    			.setIssuedAt(currentDate)
    			.setExpiration(expireDate)
    			.signWith(key())
    			.compact();
    }
    
    
    public String getUsername(String token) {
    	Claims claims = Jwts.parserBuilder()
    				.setSigningKey(key())
    				.build()
    				.parseClaimsJws(token)
    				.getBody();
    	String username = claims.getSubject();
    	return username;
    }
    
    public boolean validateToken(String token) {
    	try {
			Jwts.parserBuilder()
					.setSigningKey(key())
					.build()
					.parse(token);
			return true;
		} catch (MalformedJwtException ex ) {
			throw new BadRequestException("Invalid JWT token!");
		} catch (ExpiredJwtException e) {
			throw new BadRequestException("Jwt Token is Expired!");
		} catch (UnsupportedJwtException e) {
			throw new BadRequestException("Unsupported JWT Token");
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("JWT claims string is empty!");
		}
    }
    
    
}
