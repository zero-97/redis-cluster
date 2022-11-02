package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConditionalOnClass({ JedisCluster.class })
public class JedisClusterConfig {

    @Value("${redisson.address}")
    private String clusterNodes;

    @Value("${redisson.password}")
    private String password;

    @Value("${redisson.timeout}")
    private Integer timeout;

    @Value("${redisson.timeout}")
    private Integer commandTimeout;

    @Value("${redisson.max-active}")
    private Integer maxActive;

    @Value("${redisson.max-wait}")
    private Long maxWait;

    @Value("${redisson.max-idle}")
    private Integer maxIdle;

    @Value("${redisson.min-idle}")
    private Integer minIdle;


    @Bean
    public JedisCluster getJedisCluster() {

        String[] serverArray = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);

        return new JedisCluster(nodes, timeout, commandTimeout, maxActive, password, jedisPoolConfig);
    }
}
