package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//to  activate spring security
//@EnableGlobalMethodSecurity(prePostEnabled = true) //security will be enabled in method base
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        1. Authentication manager
        2. Authentication provider
        3. Encode password
     */

    @Autowired
    private UserDetailsService userDetailsService;

    //override method to configure Http requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). //Disable Cross Site Request Forgery
                authorizeHttpRequests(). //authorize http requests
                antMatchers("/", "index.html", "/css/*", "/js/*", "/register").permitAll(). //no authentication needed for this end point
                and().
                authorizeRequests().antMatchers("/student/**").hasRole("ADMIN"). //we are giving permission to ADMINS on class level
                // which start with "/student/.."
                        anyRequest(). //all other requests
                authenticated(). //should be authenticated
                and().
                httpBasic(); //use basic authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10); //for strength we can provide number between 4 and 31..
        // (starting from weakest to strongest. but strongest strength will take some time and energy..around 10 is enough)
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        //introduced userDetailsService and password encoder to authenticationprovider
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}