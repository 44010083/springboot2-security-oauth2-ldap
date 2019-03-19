package com.bb.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.requestMatchers()
                .antMatchers("/login", "/oauth/**","/me","/user")
                .and()
                .authorizeRequests()
                .antMatchers("/login","/me","/user").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and().csrf().disable();
    } // @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("USER");
        /*
        auth
                .ldapAuthentication()
                .userSearchFilter("cn={0}")
                .userSearchBase("ou=people,dc=openbridge,dc=cn")
                .groupSearchBase("ou=group,dc=openbridge,dc=cn")
                .groupSearchFilter("member={0}")
                .contextSource()
                .url("ldap://192.168.84.13")
                .port(389)
                .managerDn("cn=Manager,dc=openbridge,dc=cn")
                .managerPassword("123456")
        ;
        */
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
