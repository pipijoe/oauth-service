package com.example.oauthservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String nickname;
    private String password;
    private Integer state;
    private List<String> roles;
}
