package com.netcracker.travelplanner.security.config;

import com.netcracker.travelplanner.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Advanced security configuration for BootSpringSecurity
 * Extends of {@link WebSecurityConfigurerAdapter} class
 * Аннотация EnableWebSecurity отключает все настройки SpringSecurity, но
 * благодаря наследованию от класса WebSecurityConfigurerAdapter, возможно использовать механизм
 * адаптеров, позволяющие не писать с нуля настройки.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //For GUESTS users
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/signUp","/js/**","/css/**","/img/**",
                        "/fonts/**","/navbar/**","/api/**").permitAll();

        // If no login, it will redirect to /login page.
        http.formLogin()/*.loginPage("/signIn")*/.loginProcessingUrl("/singIn").usernameParameter("email")
                .passwordParameter("password").successForwardUrl("/users").failureForwardUrl("/signUp").permitAll()
                .and().logout().logoutSuccessUrl("/signIn").permitAll()
                .and().csrf().disable();

        //For ADMINS and USERS only
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "USER");

        // For ADMINS only.
        http.authorizeRequests().anyRequest().hasRole("ADMIN");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*in-memory autentification*/
         /*auth
                /*.inMemoryAuthentication()
                    .withUser("user").password("123").roles("USER")
                    .and()
                    .withUser("admin").password("321").roles("ADMIN");*/
        auth.userDetailsService(userDetailsServiceImpl);
                //.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());

    }
}
