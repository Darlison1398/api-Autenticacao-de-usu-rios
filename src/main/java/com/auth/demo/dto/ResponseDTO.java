package com.auth.demo.dto;

public record ResponseDTO (
    Long id,
    String nome,
    String role,
    String token
) {
    
}
