package com.fem.boardserver.config;

import com.fem.boardserver.login.filter.JwtAuthenticationFilter;
import com.fem.boardserver.login.filter.JwtAuthorizationFilter;
import com.fem.boardserver.login.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http
                .authorizeHttpRequests(authorization -> {
                    authorization.requestMatchers(PathRequest.toH2Console())
                                    .permitAll();

                    authorization.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                    .permitAll();

                    authorization.requestMatchers(HttpMethod.GET, "/","/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**")
                            .permitAll();

                    authorization.requestMatchers(HttpMethod.POST, "/users")
                            .permitAll();

                    authorization.anyRequest().authenticated();
                });
        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtService))
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
