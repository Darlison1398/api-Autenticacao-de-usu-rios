package com.auth.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.demo.config.security.TokenService;
import com.auth.demo.dto.LoginDTO;
import com.auth.demo.dto.ResponseDTO;
import com.auth.demo.dto.UserDTO;
import com.auth.demo.helper.SenhaValidation;
import com.auth.demo.model.UserModel;
import com.auth.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public UserModel createCountUser(UserModel user) {
        try {
            user.setDataHoraCadastro(LocalDateTime.now());
            user.setStatus(true);
            user.setRole("USER");
            user.setSenha(SenhaValidation.validarECodificarSenha(user.getSenha()));
            return userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar criar usuário: " + e.getMessage());
        }
    }

    public ResponseDTO fazerLogin(LoginDTO body) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(body.email());
        if (!optionalUser.isPresent()) {
            throw new IllegalStateException("Usuário não encontrado");
        }
        UserModel user = optionalUser.get();
        if (!passwordEncoder.matches(body.senha(), user.getSenha())) {
            throw new IllegalStateException("Senha inválida");
        }
        String token = tokenService.createToken(user);
        return new ResponseDTO(user.getId(), user.getNome(), user.getRole(), token);

    }

    public UserModel editarUsuario(Long id, UserModel newUser){
        UserModel userAtual = userRepository.findById(id)
        .orElseThrow( () -> new IllegalStateException("Usuário não encontrado"));

        userAtual.setNome(newUser.getNome());
        userAtual.setEmail(newUser.getEmail());
        userAtual.setSenha(SenhaValidation.validarECodificarSenha(newUser.getSenha()));
        userAtual.setBios(newUser.getBios());
        userAtual.setIdade(newUser.getIdade());
        return userRepository.save(userAtual);
    }
    
    public UserDTO userLogado(){
        UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = new UserDTO(user.getNome(), user.getIdade(), user.getBios(), user.getEmail(), user.isStatus(), user.getDataHoraCadastro());
        return userDTO;
    }
    
}
