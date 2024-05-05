package com.writeabyte.config;

import com.writeabyte.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtTokenProvider;
    
    private static final Set<String> PUBLIC_ENDPOINTS = new HashSet<>();
    
    static {
        // Add your public endpoints
        PUBLIC_ENDPOINTS.add("/login");
        PUBLIC_ENDPOINTS.add("/signup");
        PUBLIC_ENDPOINTS.add("/index"); // Add more as needed
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	String requestPath=request.getRequestURI();
    	if(isPublicEndpoint(requestPath)) {
    		filterChain.doFilter(request, response);
    		return;
    	}
    	
        // Extract token from Authorization header
        String token = extractToken(request);

        // Validate token
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Token is valid, proceed with the request
            filterChain.doFilter(request, response);
        } else {
            // Token is invalid, send unauthorized response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    private boolean isPublicEndpoint(String requestPath) {
        // Simple pattern matching for exact paths
        return PUBLIC_ENDPOINTS.contains(requestPath);
    }

    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
