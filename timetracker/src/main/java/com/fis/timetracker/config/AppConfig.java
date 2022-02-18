package com.fis.timetracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

import static com.fis.timetracker.config.BeanUtil.createCqlSessionFactoryBean;

@Configuration
public class AppConfig {

    @Primary
    @Bean
    @DependsOn("cassandraMigrationCqlSession")
    public CqlSessionFactoryBean cassandraSession(
            @Value("${spring.data.cassandra.keyspace-name}") final String keyspaceName,
            CassandraSessionBuilderConfigurer cassandraSessionBuilderConfigurer,
            @Value("${spring.data.cassandra.contact-points}") final String contactPoints) {

        return createCqlSessionFactoryBean(
                keyspaceName, cassandraSessionBuilderConfigurer, contactPoints);
    }

    @Bean(name = "cassandraMigrationCqlSession")
    public CqlSessionFactoryBean cassandraMigrateSession(
            @Value("${spring.data.cassandra.keyspace-name}") final String keyspaceName,
            CassandraSessionBuilderConfigurer cassandraSessionBuilderConfigurer,
            @Value("${spring.data.cassandra.contact-points}") final String contactPoints) {
        return createCqlSessionFactoryBean(
                keyspaceName, cassandraSessionBuilderConfigurer, contactPoints);
    }
}
