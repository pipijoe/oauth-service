package com.example.oauthservice.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@Data
@Builder
public class Auth2TokenVO {
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;
}
