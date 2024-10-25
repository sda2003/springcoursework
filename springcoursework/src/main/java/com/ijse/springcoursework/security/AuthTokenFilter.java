package com.ijse.springcoursework.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;

    private String parseJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer") ) {
            return authHeader.substring(7);
        } else {
            System.out.println("Authorization header is missing or does not contain a Bearer token.");
            return null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
     throws IOException, ServletException {
    
     try {
        String jwtToken = parseJwtFromHeader(request);

         if (jwtToken != null && jwtUtil.validateJwtToken(jwtToken)) {
            
                String username = jwtUtil.extractUsernameFromJwt(jwtToken);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
             }
         }  catch (Exception e) {
                e.printStackTrace();
       }

     filterChain.doFilter(request, response);

     
    }


}
