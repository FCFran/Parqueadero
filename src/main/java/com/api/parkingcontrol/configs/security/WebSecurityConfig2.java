package com.api.parkingcontrol.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*Nueva forma de configurar spring security*/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig2 {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //confgs

        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.GET,"/parking-spot/**").permitAll() //permisos todos los roles
//                .antMatchers(HttpMethod.POST,"/parking-spot").hasRole("USER") //permisos el rol user
//                .antMatchers(HttpMethod.DELETE,"/parking-spot/**").hasRole("ADMIN") //permisos para el rol admin
                .anyRequest().authenticated()
                .and()
                .csrf().disable();//desactivar la protección CSRF para acceder a los metodos post delete

        //permitAll() >> permite el acceso sin autorizacion con spring security a todos los recursos
        //authenticated() >> todas las solicitudes http deben ser autenticadas
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
