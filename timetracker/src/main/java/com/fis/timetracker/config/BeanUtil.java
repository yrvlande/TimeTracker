package com.fis.timetracker.config;

import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

public class BeanUtil {
  public static CqlSessionFactoryBean createCqlSessionFactoryBean(
      String keyspaceName,
      CassandraSessionBuilderConfigurer cassandraSessionBuilderConfigurer,
      String contactPoints) {
    CqlSessionFactoryBean bean = new CqlSessionFactoryBean();
    bean.setKeyspaceName(keyspaceName);
    bean.setContactPoints(contactPoints);
    bean.setSessionBuilderConfigurer(cassandraSessionBuilderConfigurer);
    return bean;
  }
}
