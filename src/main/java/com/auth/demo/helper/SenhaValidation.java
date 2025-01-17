package com.auth.demo.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaValidation {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        /**
     * Valida se a senha atende aos requisitos de tamanho.
     *
     * @param senha Senha a ser validada.
     * @throws IllegalArgumentException Se a senha não for válida.
     */
    public static void validarSenha(String senha) {
        if (senha == null || senha.length() < 6 || senha.length() > 12) {
            throw new IllegalArgumentException("A senha deve ter entre 6 e 12 caracteres.");
        }
    }

    /**
     * Codifica a senha usando BCrypt.
     *
     * @param senha Senha a ser codificada.
     * @return Senha codificada.
     */
    public static String codificarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    /**
     * Valida e codifica a senha.
     *
     * @param senha Senha a ser validada e codificada.
     * @return Senha codificada.
     */
    public static String validarECodificarSenha(String senha) {
        validarSenha(senha); // Valida a senha
        return codificarSenha(senha); // Codifica a senha
    }
    
}
