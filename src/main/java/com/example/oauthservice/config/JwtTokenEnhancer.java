package com.example.oauthservice.config;

import com.example.oauthservice.domain.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            if (authentication.isClientOnly()) {
                return accessToken;
            }
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            Map<String, Object> info = new HashMap<>();
            //把用户信息设置到JWT中
            info.put("id", securityUser.getId());
            info.put("nickname", securityUser.getNickname());
            info.put("username", securityUser.getUsername());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
