package br.com.ecoinsight.util;

import br.com.ecoinsight.exception.UnauthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    private static final String TOKEN = "token";
    private static final long TEMPO_MAX = 86400000;

    public static String gerarToken(int userId, String email) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("userId", userId)
                    .setExpiration(new Date(System.currentTimeMillis() + TEMPO_MAX))
                    .signWith(SignatureAlgorithm.HS256, TOKEN)
                    .compact();
        } catch (JwtException e) {
            throw new UnauthorizedException("Erro ao gerar o token JWT.", e);
        }
    }

    public static String validarToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(TOKEN)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token expirado.", e);
        } catch (JwtException e) {
            throw new UnauthorizedException("Token inválido.", e);
        }
    }

    public static int extrairUserId(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(TOKEN)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", Integer.class);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token expirado ao extrair o ID do usuário.", e);
        } catch (JwtException e) {
            throw new UnauthorizedException("Falha ao extrair o ID do usuário do token.", e);
        }
    }
}
