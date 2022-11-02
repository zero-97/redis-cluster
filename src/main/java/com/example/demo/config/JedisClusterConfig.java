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

    @Value("${jedis-cluster.cluster.nodes}")
    private String nodes;

    @Value("${jedis-cluster.cluster.password}")
    private String password;

    @Value("${jedis-cluster.cluster.connection-timeout}")
    private Integer connectionTimeout;

    @Value("${jedis-cluster.cluster.so-timeout}")
    private Integer soTimeout;

    @Value("${jedis-cluster.pool.max-active}")
    private Integer maxActive;

    @Value("${jedis-cluster.pool.max-wait}")
    private Long maxWait;

    @Value("${jedis-cluster.pool.max-idle}")
    private Integer maxIdle;

    @Value("${jedis-cluster.pool.min-idle}")
    private Integer minIdle;


    @Bean
    public JedisCluster getJedisCluster() {

        String[] serverArray = nodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.parseInt(ipPortPair[1].trim())));
        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        return new JedisCluster(nodes, connectionTimeout, soTimeout, maxActive, password, jedisPoolConfig);
    }
}
