package com.auth.demo.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.demo.model.AdminModel;
import com.auth.demo.model.UserModel;
import com.auth.demo.repository.AdminRepository;
import com.auth.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            String token = recoverToken(request);
            
            if (token != null) {
                try {
                    String login = tokenService.validarToken(token);

                    if (login != null) {
                        Optional<AdminModel> admin = adminRepository.findByEmail(login);

                        if (admin.isPresent()) {
                            AdminModel adminModel = admin.get();
                            var authentication = new UsernamePasswordAuthenticationToken(adminModel, null, adminModel.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                        } else {
                            Optional<UserModel> user = userRepository.findByEmail(login);
                            if (user.isPresent()) {
                                UserModel userModel = user.get();
                                var authentication = new UsernamePasswordAuthenticationToken(userModel, null, userModel.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }
                        }
                    }


                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Falha na autenticação: " + e.getMessage());
                    return;
                }
            }

        filterChain.doFilter(request, response);
        
    }

    private String recoverToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
}
