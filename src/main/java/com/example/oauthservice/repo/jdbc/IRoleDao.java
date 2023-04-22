package com.example.oauthservice.repo.jdbc;

import com.example.oauthservice.domain.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author joetao
 */
public interface IRoleDao extends JpaRepository<RoleDO, Long> {

}
