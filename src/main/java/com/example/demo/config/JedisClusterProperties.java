package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuanqizhang
 */
@Data
@Component
@PropertySource(value = "classpath:application-jedisCluster.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "jedis-cluster")
public class JedisClusterProperties {

    private Cluster cluster;

    private Pool pool;

    @Data
    public static class Cluster {
        private List<String> nodes;

        private String password;

        private Integer connectionTimeout;

        private Integer soTimeout;

        private Integer maxAttempts;
    }

    @Data
    public static class Pool {
        private Integer maxActive;

        private Long maxWait;

        private Integer maxIdle;

        private Integer minIdle;
    }

}
