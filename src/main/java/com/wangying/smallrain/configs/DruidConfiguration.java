package com.wangying.smallrain.configs;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

@Configuration
public class DruidConfiguration {
  
  @Value("${appdata.druid.initialSize}")
  private int initialSize;
  @Value("${appdata.druid.minIdle}")
  private int minIdle;
  @Value("${appdata.druid.maxActive}")
  private int maxActive;
  @Value("${appdata.druid.maxWait}")
  private int maxWait;
  @Value("${appdata.druid.timeBetweenEvictionRunsMillis}")
  private int timeBetweenEvictionRunsMillis;
  @Value("${appdata.druid.minEvictableIdleTimeMillis}")
  private int minEvictableIdleTimeMillis;
  @Value("${appdata.druid.validationQuery}")
  private String validationQuery;
  @Value("${appdata.druid.testWhileIdle}")
  private boolean testWhileIdle;
  @Value("${appdata.druid.testOnBorrow}")
  private boolean testOnBorrow;
  @Value("${appdata.druid.testOnReturn}")
  private boolean testOnReturn;
  @Value("${appdata.druid.poolPreparedStatements}")
  private boolean poolPreparedStatements;
  @Value("${appdata.druid.maxPoolPreparedStatementPerConnectionSize}")
  private int maxPoolPreparedStatementPerConnectionSize;
  @Value("${appdata.druid.filters}")
  private String filters;
  @Value("${appdata.druid.connectionProperties}")
  private String connectionProperties;
  
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Bean // 声明其为Bean实例
  @Primary // 在同样的DataSource中，首先使用被标注的DataSource
  public DataSource dataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(url);
    datasource.setUsername(username);
    datasource.setPassword(password);
    datasource.setDriverClassName(driverClassName);

    // configuration
    datasource.setInitialSize(initialSize);
    datasource.setMinIdle(minIdle);
    datasource.setMaxActive(maxActive);
    datasource.setMaxWait(maxWait);
    datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    datasource.setValidationQuery(validationQuery);
    datasource.setTestWhileIdle(testWhileIdle);
    datasource.setTestOnBorrow(testOnBorrow);
    datasource.setTestOnReturn(testOnReturn);
    datasource.setPoolPreparedStatements(poolPreparedStatements);
    datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    try {
      datasource.setFilters(filters);
    } catch (SQLException e) {
      System.err.println("druid configuration initialization filter: " + e);
    }
    datasource.setConnectionProperties(connectionProperties);
    return datasource;
  }
  
  @Bean
  public ServletRegistrationBean<StatViewServlet> druidServlet() {
    ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(
        new StatViewServlet(), "/druid/*");
    // IP白名单
    servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    // IP黑名单(共同存在时，deny优先于allow)
    // servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
    // 控制台管理用户
    servletRegistrationBean.addInitParameter("loginUsername", "admin");
    servletRegistrationBean.addInitParameter("loginPassword", "wy16894255");
    // 是否能够重置数据 禁用HTML页面上的“Reset All”功能
    servletRegistrationBean.addInitParameter("resetEnable", "false");
    return servletRegistrationBean;
  }

}
