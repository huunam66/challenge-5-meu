package com.challenge3.app.configuration.auth.jwt.filter;

import com.challenge3.app.configuration.auth.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsManager userDetailsManager;

    public JwtRequestFilter(JwtService jwtService,
                            UserDetailsManager userDetailsManager
                            ) {
        this.jwtService = jwtService;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

//        System.out.println("Hello doFilterInternal!");

        String authHeader = request.getHeader("Authorization");
//        System.out.println(request);
//        System.out.println(authHeader);

        if(authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);
//            System.out.println(token);
            String username = jwtService.extractToken(token);

            if(username != null) {
                UserDetails userDetails = this.userDetailsManager.loadUserByUsername(username);

                if(jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}


// try{
//         if(authHeader != null && authHeader.startsWith("Bearer")) {
//String token = authHeader.substring(7);
//String username = jwtService.extractToken(token);
//
//                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                    if(jwtService.validateToken(token, userDetails)) {
//UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
//                            }
//                            }
//                            }
//                            catch (NullPointerException | ExpiredJwtException ignored){}