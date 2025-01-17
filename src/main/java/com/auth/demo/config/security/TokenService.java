package com.auth.demo.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth.demo.model.AdminModel;
import com.auth.demo.model.PessoaModel;
import com.auth.demo.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /*public String createToke(AdminModel admin){
        return createToken(admin.getEmail());
    }

    public String createToken(UserModel user){
        return createToken(user.getEmail());
    }*/

    public String createToken(PessoaModel pessoa){
        return createToken(pessoa.getEmail());
    }

    private String createToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("login-auth-api")
                    .withSubject(email)
                    .withExpiresAt(gerarDataHoraExpiracao())
                    .sign(algorithm);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar fazer autenticação", e);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido", e);
        }
    }

    private Instant gerarDataHoraExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    
}
