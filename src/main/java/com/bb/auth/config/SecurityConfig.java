package com.bb.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
    //MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ClassLoader.getSystemResourceAsStream("application.properties");
        String usedLdap = "";
        String user = "";
        String passwd = "";
        try {
            // 使用properties对象加载输入流
            properties.load(in);
            // 获取key对应的value值
            usedLdap = properties.getProperty("used.ldap");
            user = properties.getProperty("local.user");
            passwd = properties.getProperty("local.passwd");
        } catch (Exception e) {
        }
        if ("yes".equalsIgnoreCase(usedLdap)) {


        } else {
            //auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser(user)
            //		.password(new BCryptPasswordEncoder().encode(passwd)).authorities("ROLE_ADMIN");

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
                    .managerPassword("123456");
        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest()
                .authenticated().and().formLogin().and().logout().permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/config/**", "/css/**", "/fonts/**", "/img/**", "/js/**");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	/*
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}


*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
