package com.tahir.where_did_my_money_go.auth.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tahir.where_did_my_money_go.auth.util.JwtUtil;
import com.tahir.where_did_my_money_go.common.exception.EmailNotVerifiedException;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        // ✅ Allow auth endpoints without JWT
        String path = request.getServletPath();
        System.out.println("Current request path: " + path);
        if (path.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (!jwtUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        var userId = jwtUtil.extractUserId(token);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(
                userId,
                null,
                null,
                List.of(user.getRole()),
                user.isVerified());

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            if (!customUserDetails.isVerified()) {
                System.out.println("Email not verified for user: " + userId);
                throw new EmailNotVerifiedException("Email not verified");
            }
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}