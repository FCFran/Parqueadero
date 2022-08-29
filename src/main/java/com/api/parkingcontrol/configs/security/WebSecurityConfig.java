package com.api.parkingcontrol.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*inseccion  de dependencia para trabajar con usuarios de la bd*/
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET,"/parking-spot/**").permitAll() //permisos todos los roles
                .antMatchers(HttpMethod.POST,"/parking-spot").hasRole("USER") //permisos el rol user
                .antMatchers(HttpMethod.DELETE,"/parking-spot/**").hasRole("ADMIN") //permisos para el rol admin
                .anyRequest().authenticated()
                .and()
                .csrf().disable();//desactivar la protección CSRF para acceder a los metodos post delete

        //permitAll() >> permite el acceso sin autorizacion con spring security a todos los recursos
        //authenticated() >> todas las solicitudes http deben ser autenticadas
        }


        //Método para crear usuarios personalizados en memoria
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.inMemoryAuthentication()
                .withUser("michelli")
                .password(passwordEncoder().encode("607080"))
                .roles("ADMIN");
    }*/

    // Usuario de la base de Datos
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //encriptar el password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
