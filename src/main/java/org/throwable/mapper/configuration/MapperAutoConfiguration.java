package org.throwable.mapper.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.throwable.mapper.configuration.prop.PropertiesConfiguration;
import org.throwable.mapper.configuration.prop.SmartMapperProperties;
import org.throwable.mapper.support.assist.MapperTemplateAssistor;

import javax.sql.DataSource;
import java.util.stream.Stream;

/**
 * @author throwable
 * @version v1.0
 * @description 动态注册mapper
 * @since 2017/4/4 0:14
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean({DataSource.class, SqlSessionFactory.class})
@EnableConfigurationProperties({SmartMapperProperties.class})
public class MapperAutoConfiguration implements InitializingBean {

	private final SqlSessionFactory sqlSessionFactory;

	private final SmartMapperProperties properties;

	public MapperAutoConfiguration(SqlSessionFactory sqlSessionFactory, SmartMapperProperties properties) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.properties = properties;
	}

	/**
	 * 关键步骤
	 * 1:注册SmartMapper
	 * 2:动态注册所有自定义的MappedStatement
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		registryMappers();
		dynamicRegistryMappedStatements();
	}

	private void registryMappers() {
		PropertiesConfiguration configuration = properties.createConfiguration();
		MapperTemplateAssistor assistor = new MapperTemplateAssistor(configuration);
		Stream.of(configuration.getRegisterMappers()).forEach(assistor::registerMapper);
		assistor.processConfiguration(sqlSessionFactory.getConfiguration());
	}

	private void dynamicRegistryMappedStatements() {

	}

}
