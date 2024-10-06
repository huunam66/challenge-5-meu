package com.challenge3.app.configuration.security;

import com.challenge3.app.configuration.auth.jwt.filter.JwtRequestFilter;
import com.challenge3.app.configuration.auth.role.ROLE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig implements WebMvcConfigurer  {

    private final String[] URL_PERMIT_ALL = new String[]{
            "/api/auth/signin",

            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(AccessDeniedHandler accessDeniedHandler,
                             AuthenticationEntryPoint authenticationEntryPoint,
                             JwtRequestFilter jwtRequestFilter) {

        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors().and().csrf().disable()

//                ------------------------------------------------------------------

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

//                ------------------------------------------------------------------

                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .requestMatchers(URL_PERMIT_ALL).permitAll()

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        new AntPathRequestMatcher("/api/auth/grant", HttpMethod.PUT.name()),
                        new AntPathRequestMatcher("/api/users/**", HttpMethod.DELETE.name()),
                        new AntPathRequestMatcher("/api/products/**", HttpMethod.DELETE.name())
                ).hasRole(ROLE.SUPER_ADMIN.name())

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        new AntPathRequestMatcher("/api/users", HttpMethod.POST.name()),
                        new AntPathRequestMatcher("/api/products", HttpMethod.POST.name()),
                        new AntPathRequestMatcher("/api/products/**", HttpMethod.PUT.name())
                ).hasRole(ROLE.ADMIN.name())

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                                "/api/users",
                                "/api/users/**"
                ).hasRole(ROLE.ADMIN.name())

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                                "/api/products/**",
                                "/api/products/search/**",
                                "/api/products/filter"
                ).hasAnyRole(ROLE.USER.name(), ROLE.ADMIN.name())

//                ------------------------------------------------------------------

                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()

//                ------------------------------------------------------------------

                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


