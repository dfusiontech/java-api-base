package com.dfusiontech.server.config;

import com.dfusiontech.server.repository.jpa.core.CoreRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Data Source and Transaction configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {"com.dfusiontech.*.repository"},
	repositoryBaseClass = CoreRepositoryImpl.class,
	transactionManagerRef = "transactionManager",
	entityManagerFactoryRef = "entityManagerFactory"
)
@EnableJpaAuditing
@Slf4j
public class JpaConfig implements TransactionManagementConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaConfig.class);

	/**
	 * Creating default Transaction manager
	 *
	 * @return
	 */
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new JpaTransactionManager();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
