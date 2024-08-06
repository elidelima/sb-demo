package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationBasicAuth {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("1"))
                .roles("ADMIN", "USER")
//                .authorities("BASIC", "SPECIAL")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("2"))
                .roles("USER")
//                .authorities("BASIC")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .anyRequest()
                .authenticated()
//                .requestMatchers(HttpMethod.GET, "/special").hasAuthority("SPECIAL")
//                .requestMatchers(HttpMethod.GET, "/basic").hasAnyAuthority("BASIC", "SPECIAL")
//                .requestMatchers(HttpMethod.GET, "/special").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/basic").hasAnyRole("ADMIN", "USER")
//                .requestMatchers("/open")
//                .permitAll()
//                .requestMatchers("/closed")
//                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();


    }
}
