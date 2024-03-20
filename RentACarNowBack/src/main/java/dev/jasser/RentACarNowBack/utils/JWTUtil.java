package dev.jasser.RentACarNowBack.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

@Component
public class JWTUtil {
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return(userName.equals(userDetails.getUserName()))&& !isTokenExpired(token);
	}
	private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	private String generateToken(Map<String, Object>extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
	}
	public String generateRefreshToken(Map<String,Object>extraClaims, UserDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+ 604800000))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
	}
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
				.getBody();
	}
	private Key getSigninKey() {
		byte[] keyBytes = Decoders.BASE64.decode("413F4428472B46250655368566D5970337336763979244226452948404D6351");
		return Keys.nmacShakeyFor(keyBytes);
	}
}
