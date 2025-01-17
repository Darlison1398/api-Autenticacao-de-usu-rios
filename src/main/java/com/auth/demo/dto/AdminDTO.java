package com.auth.demo.dto;

import java.time.LocalDateTime;

public record AdminDTO (
    String nome,
    String code,
    String email,
    LocalDateTime dataHoraCadastro
 
) {
    
}
