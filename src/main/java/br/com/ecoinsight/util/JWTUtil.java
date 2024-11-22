package br.com.ecoinsight.util;

import br.com.ecoinsight.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtil {
    private static final SecretKey TOKEN = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long TEMPO_MAX = 86400000; // 1 dia

    public static String gerarToken(int userId, String email) {
        try {
            if (userId <= 0) {
                throw new IllegalArgumentException("userId deve ser maior que zero.");
            }
            if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                throw new IllegalArgumentException("Email inválido.");
            }

            System.out.println("Gerando token com userId=" + userId + " e email=" + email);

            return Jwts.builder()
                    .setSubject(email)
                    .claim("userId", userId)
                    .setExpiration(new Date(System.currentTimeMillis() + TEMPO_MAX))
                    .signWith(TOKEN)
                    .compact();

        } catch (JwtException e) {
            System.err.println("Erro ao gerar token: " + e.getMessage());
            e.printStackTrace();
            throw new UnauthorizedException("Erro ao gerar o token JWT.", e);
        }
    }

    public static String validarToken(String token) {
        try {
            System.out.println("Validando token: " + token);

            return Jwts.parserBuilder()
                    .setSigningKey(TOKEN)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.err.println("Token expirado: " + e.getMessage());
            throw new UnauthorizedException("Token expirado.", e);
        } catch (JwtException e) {
            System.err.println("Token inválido: " + e.getMessage());
            throw new UnauthorizedException("Token inválido.", e);
        }
    }

    public static int extrairUserId(String token) {
        try {
            System.out.println("Extraindo userId do token: " + token);

            return Jwts.parserBuilder()
                    .setSigningKey(TOKEN)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", Integer.class);
        } catch (ExpiredJwtException e) {
            System.err.println("Token expirado ao extrair o ID do usuário: " + e.getMessage());
            throw new UnauthorizedException("Token expirado ao extrair o ID do usuário.", e);
        } catch (JwtException e) {
            System.err.println("Falha ao extrair o ID do usuário do token: " + e.getMessage());
            throw new UnauthorizedException("Falha ao extrair o ID do usuário do token.", e);
        }
    }
}
