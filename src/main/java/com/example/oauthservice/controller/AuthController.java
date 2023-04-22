package com.example.oauthservice.controller;

import com.example.oauthservice.domain.ResultJson;
import com.example.oauthservice.domain.vo.Auth2TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@RestController
@RequestMapping("/oauth")
@Api(tags = "登录")
public class AuthController {
    private final TokenEndpoint tokenEndpoint;

    public AuthController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token", method=RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResultJson<Auth2TokenVO> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Auth2TokenVO auth2Token = Auth2TokenVO.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken() != null ? oAuth2AccessToken.getRefreshToken().getValue() : null)
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return ResultJson.ok(auth2Token);
    }
}
