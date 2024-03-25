package com.hdu.hdufpga.configuration;

import com.hdu.hdufpga.service.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

@EnableAuthorizationServer
@Configuration
public class Oauth2Configuration extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager manager;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    AuthService authService;

    @Resource
    TokenStore store;

    @Resource
    JwtAccessTokenConverter converter;

    private AuthorizationServerTokenServices serverTokenServices(){  //这里对AuthorizationServerTokenServices进行一下配置
        DefaultTokenServices services = new DefaultTokenServices();
        services.setSupportRefreshToken(true);   //允许Token刷新
        services.setTokenStore(store);   //添加刚刚的TokenStore
        services.setTokenEnhancer(converter);   //添加Token增强，其实就是JwtAccessTokenConverter，增强是添加一些自定义的数据到JWT中
        return services;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("hdu-fpga")
                .secret(passwordEncoder.encode("yy5201314"))
                .autoApprove(true)
                .scopes("all")
                .authorizedGrantTypes("password")
                ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenServices(serverTokenServices())
                .userDetailsService(authService)
                .authenticationManager(manager);
    }
}
