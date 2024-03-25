package com.hdu.hdufpga.token;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

@Component
public class JwtTk {
    @Bean
    @Qualifier("JwtAccessTokenConverter")
    public JwtAccessTokenConverter tokenConverter(){  //Token转换器，将其转换为JWT
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("yy5201314");   //这个是对称密钥，一会资源服务器那边也要指定为这个
        return converter;
    }

    @Bean
    public TokenStore tokenStore(@Qualifier("JwtAccessTokenConverter") JwtAccessTokenConverter jwtAccessTokenConverter){  //Token存储方式现在改为JWT存储
        return new JwtTokenStore(jwtAccessTokenConverter);  //传入刚刚定义好的转换器
    }
}
