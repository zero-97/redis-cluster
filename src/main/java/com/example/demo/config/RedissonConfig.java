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

    @Bean
    public RedissonClient getRedisClient() {
        String[] nodes = address.split(",");
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }

        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(scanInterval)
                .addNodeAddress(nodes).setRetryAttempts(retryAttempts).setTimeout(timeout);
        if (StringUtils.isNotEmpty(password)) {
            config.useClusterServers().setPassword(password);
        }
        return Redisson.create(config);
    }
}