package com.auth.demo.dto;

import java.time.LocalDateTime;

public record UserDTO (
    String nome,
    int idade,
    String bios,
    String email,
    boolean status,
    LocalDateTime dataHoraCadastro
 ) {}

