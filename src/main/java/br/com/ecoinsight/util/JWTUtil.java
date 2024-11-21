package br.com.ecoinsight.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    private static final String TOKEN = "token";
    private static final long TEMPO_MAX = 86400000;

    public static String gerarToken(int userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_MAX))
                .signWith(SignatureAlgorithm.HS256, TOKEN)
                .compact();
    }

    public static String validarToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(TOKEN)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static int extrairUserId(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(TOKEN)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Integer.class);
    }
}
