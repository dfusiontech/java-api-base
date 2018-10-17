package com.dfusiontech.server.config;

import com.dfusiontech.server.repository.jpa.core.CoreRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * Data Source and Transaction configuration.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {"com.dfusiontech.*.repository"},
	repositoryBaseClass = CoreRepositoryImpl.class
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

}
