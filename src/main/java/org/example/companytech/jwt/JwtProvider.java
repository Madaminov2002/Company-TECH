package org.example.companytech.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.example.companytech.domain.Company;
import org.example.companytech.domain.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${secret.key}")
    private String secretKey;

    public String generateForEmployee(Employee employee) {
        StringJoiner roles = new StringJoiner(",");
        employee.getRoles().forEach(role -> roles.add(role.getName()));
        return Jwts.builder()
                .subject(employee.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 7200000))
                .claim("roles", roles.toString())
                .signWith(key())
                .compact();
    }

    public String generateForCompany(Company company) {
        Integer password = new Random().nextInt(100000, 1000000);
        StringJoiner roles = new StringJoiner(",");
        company.getRoles().forEach(role -> roles.add(role.getName()));
        return Jwts.builder()
                .subject(company.getUserName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000 * 1000))
                .claim("roles", roles.toString())
                .claim("password", password.toString())
                .claim("email", company.getEmail())
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(final String token) {
        try {
            Claims claims = parse(token);
            if (claims.getExpiration().after(new Date())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
