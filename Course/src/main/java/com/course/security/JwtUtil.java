package com.course.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "9SuY3LJxubPRkBs2CvjkdSd7MI3gxnNw5CzwHCBYtX7";
	private static final long EXPIRATION_TIME = 864_000_000;
	
	private SecretKey getSigningKey() {
		byte[] keyInBytes = SECRET_KEY.getBytes();
		return Keys.hmacShaKeyFor(keyInBytes);
	}
	
	// 1. Generate Token
	public String generateToken(String username) {
		return Jwts.builder()
					  .subject(username)
					  .issuedAt(new Date(System.currentTimeMillis()))
					  .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					  .signWith(getSigningKey(), Jwts.SIG.HS256)
					  .compact();
	}
	
	// 2. Extract username
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	// 3. Validate Token
	public boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaim(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaim(String token) {
		return Jwts.parser()
					  .verifyWith(getSigningKey())
					  .build()
					  .parseSignedClaims(token)
					  .getPayload();
	}
	
}