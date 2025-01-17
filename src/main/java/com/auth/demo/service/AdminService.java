package com.auth.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.demo.config.security.TokenService;
import com.auth.demo.dto.AdminDTO;
import com.auth.demo.dto.LoginDTO;
import com.auth.demo.dto.ResponseDTO;
import com.auth.demo.helper.SenhaValidation;
import com.auth.demo.model.AdminModel;
import com.auth.demo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminModel createCountAdmin(AdminModel admin) {
        admin.setDataHoraCadastro(LocalDateTime.now());
        admin.setRole("ADMIN");
        admin.setSenha(SenhaValidation.validarECodificarSenha(admin.getSenha()));
        return adminRepository.save(admin);
    }

    public ResponseDTO fazerLogin(LoginDTO body) {
        Optional<AdminModel> optionalAdmin = adminRepository.findByEmail(body.email());
        if (!optionalAdmin.isPresent()) {
            throw new IllegalStateException("User admin não encontrado");
        }

        AdminModel admin = optionalAdmin.get();
        if (!passwordEncoder.matches(body.senha(), admin.getSenha())) {
            throw new IllegalStateException("Senha inválida");
        }
        String token = tokenService.createToken(admin);
        return new ResponseDTO(admin.getId(), admin.getNome(), admin.getRole(), token);

    }

    public AdminModel editarAdmin(Long id, AdminModel newAdmin) {
        AdminModel adminAtual = adminRepository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Admin não encontrado"));

        adminAtual.setNome(newAdmin.getNome());
        adminAtual.setCode(newAdmin.getCode());
        adminAtual.setEmail(newAdmin.getEmail());
        adminAtual.setSenha(newAdmin.getSenha());

        return adminRepository.save(adminAtual);
    }

    public AdminDTO adminLogado() {
        AdminModel admin = (AdminModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AdminDTO adminDTO = new AdminDTO(admin.getNome(), admin.getCode(), admin.getEmail(), admin.getDataHoraCadastro());
        return adminDTO;
    }
    
}
