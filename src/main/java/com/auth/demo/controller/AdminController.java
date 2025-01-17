package com.auth.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.demo.dto.AdminDTO;
import com.auth.demo.dto.LoginDTO;
import com.auth.demo.dto.ResponseDTO;
import com.auth.demo.model.AdminModel;
import com.auth.demo.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<String> saveAdmin(@Valid @RequestBody AdminModel adminModel){
        try {
            AdminModel admiNew = adminService.createCountAdmin(adminModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar admin: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDTO body){
        try {
            ResponseDTO response = adminService.fazerLogin(body);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer login: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    @PreAuthorize("hasHole('ADMIN')")
    public ResponseEntity<String> updateAdmin(@Valid @PathVariable Long id, @RequestBody AdminModel admin) {
        try {
            AdminModel upAdmin = adminService.editarAdmin(id, admin);
            return ResponseEntity.status(HttpStatus.OK).body("Admin atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar admin: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasHole('ADMIN')")
    public ResponseEntity<?> getCurrentAdmin() {
        try {
            AdminDTO adminLogado = adminService.adminLogado();
            return ResponseEntity.status(HttpStatus.OK).body(adminLogado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar admin logado: " + e.getMessage());
        }
    }
    
}
