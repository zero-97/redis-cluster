package com.example.demo.config;

import io.micrometer.core.instrument.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonProperties properties;

    @Bean(name = "redissonClient")
    public RedissonClient getRedisClient() {
        List<String> nodes = properties.getCluster().getNodes();
        String[] nodeArr = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            nodeArr[i] = "redis://" + nodes.get(i);
        }
        Config config = new Config();

        config.useClusterServers()                                                  // 使用集群
                .setScanInterval(properties.getCluster().getScanInterval())     // 设置集群状态扫描时间
                .addNodeAddress(nodeArr)                                        // 集群节点
                .setRetryAttempts(properties.getCluster().getRetryAttempts())   // 命令失败重试次数
                .setTimeout(properties.getCluster().getTimeout());              // 超时时间
        if (StringUtils.isNotEmpty(properties.getCluster().getPassword())) {
            config.useClusterServers().setPassword(properties.getCluster().getPassword());
        }
        return Redisson.create(config);
    }
}