package com.auth.demo.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class AdminModel extends PessoaModel implements UserDetails {

    @Column(nullable = false)
    @NotBlank(message = "O código não pode estar vazio")
    @Size(min = 5, max = 6, message = "A código admin deve ter entre 5 e 6 caracteres.")
    private String code;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_ADMIN");
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
    
}
