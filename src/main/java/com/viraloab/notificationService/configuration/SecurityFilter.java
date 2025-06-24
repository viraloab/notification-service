package com.viraloab.notificationService.configuration;

import com.viraloab.notificationService.pojo.Admin;
import com.viraloab.notificationService.repository.AdminRepository;
import com.viraloab.notificationService.utils.AESCipherUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${client.host.uri}")
    private String clientURI;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AESCipherUtil encryptionUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", clientURI);
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, usr, x-api-key");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        String encryptedKey = request.getHeader("x-api-key");
        String encryptedUsr = request.getHeader("usr");
        if (!StringUtils.hasText(encryptedKey) && !StringUtils.hasText(encryptedUsr)) {
            response.sendError(401);
            return;
        }
        if (StringUtils.hasText(encryptedKey) && StringUtils.hasText(encryptedUsr)) {
            try {
                String usr = encryptionUtil.decrypt(encryptedUsr);
                Optional<Admin> dbKey = adminRepository.findByOrganisation(usr);
                if (dbKey.isPresent()) {
                    String apiKey = encryptionUtil.decrypt(encryptedKey);
                    boolean isKeyMatch = encryptionUtil.decrypt(dbKey.get().getApiKey()).equals(apiKey);
                    if (isKeyMatch && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dbKey, null, null);
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(token);
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                } else {
                    response.sendError(401);
                }
            } catch (Exception e) {
                response.sendError(401);
            }
        }
    }
}
