package org.enlightenseries.DomainDictionary.infrastructure.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfiguration {

  @Autowired
  @Bean
  public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);

    ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(
      new DefaultResourceLoader());

    // MyBatis のコンフィグレーションファイル
    bean.setConfigLocation(resolver.getResource("classpath:config/mybatis.xml"));
    // MyBatis で使用する SQL ファイル群
    bean.setMapperLocations(resolver.getResources("classpath:query/*.xml"));

    return new SqlSessionTemplate(bean.getObject());
  }
}
