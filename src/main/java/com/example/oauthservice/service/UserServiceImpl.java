package com.example.oauthservice.service;

import cn.hutool.core.collection.CollUtil;
import com.example.oauthservice.constant.MessageConstant;
import com.example.oauthservice.constant.SecurityConstant;
import com.example.oauthservice.domain.SecurityUser;
import com.example.oauthservice.domain.UserDTO;
import com.example.oauthservice.domain.entity.RoleDO;
import com.example.oauthservice.domain.entity.UserDO;
import com.example.oauthservice.repo.jdbc.IRoleDao;
import com.example.oauthservice.repo.jdbc.IUserDao;
import com.example.oauthservice.repo.jdbc.IUserRoleDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private List<UserDTO> userList;
    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final IUserRoleDao userRoleDao;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        log.info("初始化管理员账号信息");
        String password = passwordEncoder.encode("Les1028!@#$");
        userList = new ArrayList<>();
        userList.add(new UserDTO(10000L,"admin", "管理员", password,1, CollUtil.toList("ADMIN")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> findUserList = userList.stream().filter(item -> item.getName().equals(username)).collect(Collectors.toList());

        if (CollUtil.isEmpty(findUserList)) {
            Optional<UserDO> opt = userDao.findByUsername(username);
            if (!opt.isPresent()){
                throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
            }
            UserDO user = opt.get();

            Set<GrantedAuthority> grantedAuthorities = userRoleDao.findByUserId(user.getId()).stream()
                    .map(role -> new SimpleGrantedAuthority(roleDao.findById(role.getRoleId()).orElse(new RoleDO(0L, SecurityConstant.ROLE_ANONYMOUS, "匿名用户")).getRoleName()))
                    .collect(Collectors.toSet());
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getNickname(), user.getPassword(), user.getState(), grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            findUserList.add(userDTO);
        }
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}
