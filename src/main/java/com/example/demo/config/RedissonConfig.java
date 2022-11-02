package com.example.demo.config;

import io.micrometer.core.instrument.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value(value = "${redisson.address}")
    private String address;

    @Value(value = "${redisson.password}")
    private String password;

    @Value(value = "${redisson.scanInterval}")
    private int scanInterval;

    @Value(value = "${redisson.retryAttempts}")
    private int retryAttempts;

    @Value(value = "${redisson.timeout}")
    private int timeout;

    @Value("${redisson.max-active}")
    private Integer maxActive;

    @Value("${redisson.max-wait}")
    private Long maxWait;

    @Value("${redisson.max-idle}")
    private Integer maxIdle;

    @Value("${redisson.min-idle}")
    private Integer minIdle;

    @Bean(name = "redissonClient")
    public RedissonClient getRedisClient() {
        String[] nodes = address.split(",");
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }

        Config config = new Config();

        // 不进行配置，会出现异常：Only 0 of 2 slaves were synced
        config = config.setCheckLockSyncedSlaves(false);

        config.useClusterServers()                  // 使用集群
                .setScanInterval(scanInterval)      // 设置集群状态扫描时间
                .addNodeAddress(nodes)              // 集群节点
                .setRetryAttempts(retryAttempts)    // 命令失败重试次数
                .setTimeout(timeout);               // 超时时间
        if (StringUtils.isNotEmpty(password)) {
            config.useClusterServers().setPassword(password);
        }
        return Redisson.create(config);
    }
}