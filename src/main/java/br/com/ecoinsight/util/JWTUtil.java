package br.com.ecoinsight.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET = "token";
    private static final long EXPIRATION_TIME = 86400000;

    // Gerar um token com userId e email como claims
    public static String gerarToken(int userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId) // Adiciona o userId como claim
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // Validar o token e retornar o email
    public static String validarToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extrair o userId do token
    public static int extrairUserId(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Integer.class); // Extrai o userId como Integer
    }
}
