package com.example.oauthservice.repo.jdbc;

import com.example.oauthservice.domain.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author joetao
 */
public interface IUserDao extends JpaRepository<UserDO, Long> {
    Optional<UserDO> findByUsername(String username);

}
