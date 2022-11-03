package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConditionalOnClass({ JedisCluster.class })
public class JedisClusterConfig {

    @Autowired
    private JedisClusterProperties properties;

    @Bean(name = "jedisCluster")
    public JedisCluster getJedisCluster() {

        List<String> nodes = properties.getCluster().getNodes();
        Set<HostAndPort> node = new HashSet<>();
        for (String ipPort : nodes) {
            String[] ipPortPair = ipPort.split(":");
            node.add(new HostAndPort(ipPortPair[0].trim(), Integer.parseInt(ipPortPair[1].trim())));
        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(properties.getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(properties.getPool().getMaxWait());
        jedisPoolConfig.setMaxIdle(properties.getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(properties.getPool().getMinIdle());

        return new JedisCluster(node,
                properties.getCluster().getConnectionTimeout(),
                properties.getCluster().getSoTimeout(),
                properties.getCluster().getMaxAttempts(),
                properties.getCluster().getPassword(),
                jedisPoolConfig);
    }
}
