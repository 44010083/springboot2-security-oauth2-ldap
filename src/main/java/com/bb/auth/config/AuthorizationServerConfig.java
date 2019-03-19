package com.bb.auth.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Resource
	DataSource dataSource;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        clients.inMemory()
            .withClient("client")
            .secret(passwordEncoder.encode("client"))
            .authorizedGrantTypes("authorization_code")
            .scopes("all")
            .autoApprove(true)
            .redirectUris("/user/me","http://localhost:8082/ui/login","http://localhost:8083/ui2/login","http://localhost:8082/login","http://www.example.com/")
        // .accessTokenValiditySeconds(3600)
        ; // 1 hour
        */
		//这个地方指的是从jdbc查出数据来存储
		clients.withClientDetails(clientDetails());

	}

	private ClientDetailsService clientDetails() {

		return new JdbcClientDetailsService(dataSource);
	}


	@Bean
	public TokenStore tokenStore() {
//            TokenStore tokenStore = new InMemoryTokenStore();
		return new JdbcTokenStore(dataSource);
	}
	@Primary
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore());
		//.userDetailsService(userDetailsService)
		//.authenticationManager(authenticationManager);
		//endpoints.tokenServices(defaultTokenServices());
		// 配置TokenServices参数
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(false);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
		endpoints.tokenServices(tokenServices);

	}
}
