package com.taskmanger.app.tm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final CustomUserDetails userDetails;

    @Autowired
    public SecurityConfig(CustomUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().
                disable().
                authorizeHttpRequests().
                antMatchers("/auth/**").permitAll().
                anyRequest().authenticated().
                and().
                httpBasic().and().
                logout().logoutUrl("/logout").
                invalidateHttpSession(true).
                clearAuthentication(true).
                deleteCookies("JSESSIONID").
                permitAll();

        return http.authenticationProvider(daoAuthenticationProvider()).build();

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetails);
        provider.setPasswordEncoder(this.passwordEncoder());

        return  provider;
    }

    /*
    // old way of configuring

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.
                    csrf().
                    disable().
                    authorizeHttpRequests().
                    antMatchers("/auth/**").permitAll().
                    anyRequest().authenticated().
                and().
                    httpBasic().and().
                    logout().logoutUrl("/logout").
                    invalidateHttpSession(true).
                    clearAuthentication(true).
                    deleteCookies("JSESSIONID").
                    permitAll();

    }

     */




}
