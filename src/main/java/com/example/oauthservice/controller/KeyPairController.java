package com.example.oauthservice.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "公钥")
public class KeyPairController {

    private final KeyPair keyPair;

    public KeyPairController(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/rsa/publicKey")
    @ApiOperation(value = "获取公钥")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
