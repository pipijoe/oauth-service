package com.example.oauthservice.service;

import cn.hutool.core.collection.CollUtil;
import com.example.oauthservice.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Joetao
 * @date 2022/5/25
 */
@Service
@Slf4j
public class ResourceServiceImpl {
    private final RedisTemplate<String,Object> redisTemplate;

    public ResourceServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initData() {
        log.info("初始化管理员权限");
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/us/users", CollUtil.toList("ADMIN"));

        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }

}
