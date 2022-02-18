package com.fis.timetracker.config;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CassandraSessionBuilderConfigurer implements SessionBuilderConfigurer {

  private final String truststore;
  private final char[] truststorePassword;
  private final String username;
  private final String password;
  private final String localDataCenter;
  private final boolean ssl;

  public CassandraSessionBuilderConfigurer(
      @Value("${spring.data.cassandra.truststore}") final String truststore,
      @Value("${spring.data.cassandra.truststorePassword}") final char[] truststorePassword,
      @Value("${spring.data.cassandra.username}") final String username,
      @Value("${spring.data.cassandra.password}") final String password,
      @Value("${spring.data.cassandra.localDataCenter}") final String localDataCenter,
      @Value("${spring.data.cassandra.ssl}") final boolean ssl) {
    this.truststore = truststore;
    this.truststorePassword = truststorePassword;
    this.username = username;
    this.password = password;
    this.localDataCenter = localDataCenter;
    this.ssl = ssl;
  }

  @Override
  public CqlSessionBuilder configure(CqlSessionBuilder sessionBuilder) {
    if (ssl) {
      SSLContext sslContext = buildSslContext(truststore, truststorePassword);
      sessionBuilder.withSslContext(sslContext);
    }
    sessionBuilder
        .withLocalDatacenter(localDataCenter)
        .withAuthCredentials(username, password)
        .build();

    return sessionBuilder;
  }

  private SSLContext buildSslContext(String truststore, char[] truststorePassword) {
    SSLContext sslContext;
    try {
      KeyStore trustStore = KeyStore.getInstance("JKS");
      InputStream stream = new ClassPathResource(truststore).getInputStream();
      trustStore.load(stream, truststorePassword);
      Arrays.fill(truststorePassword, '\0');

      TrustManagerFactory trustManagerFactory =
          TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      trustManagerFactory.init(trustStore);

      sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
    } catch (IOException
        | KeyStoreException
        | NoSuchAlgorithmException
        | CertificateException
        | KeyManagementException e) {
      throw new BeanCreationException(
          "Unable to configure ssl context for cassandra configuration", e);
    }
    return sslContext;
  }
}
