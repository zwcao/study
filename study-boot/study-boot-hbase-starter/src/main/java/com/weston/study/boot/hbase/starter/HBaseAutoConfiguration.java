package com.weston.study.boot.hbase.starter;

import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseAutoConfiguration {

    private final HBaseProperties properties;

    public HBaseAutoConfiguration(HBaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HbaseTemplate hbaseTemplate() {
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        hbaseTemplate.setConfiguration(configuration());
        hbaseTemplate.setAutoFlush(true);
        return hbaseTemplate;
    }

    public org.apache.hadoop.conf.Configuration configuration() {

        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();

        Map<String, String> config = properties.getConfig();
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            configuration.set(key, config.get(key));
        }

        return configuration;
    }
}
