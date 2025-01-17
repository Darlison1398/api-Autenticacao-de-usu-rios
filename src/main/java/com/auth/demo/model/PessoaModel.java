package com.auth.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class PessoaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O email não pode estar vazio")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "O senha não pode estar vazia")
    private String senha;

    @Column
    private LocalDateTime dataHoraCadastro;

    @Column(nullable = false)
    private String role;

}
