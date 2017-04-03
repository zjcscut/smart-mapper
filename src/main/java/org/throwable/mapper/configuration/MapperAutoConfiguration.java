package org.throwable.mapper.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@EnableConfigurationProperties({SmartMapperProperties.class})
public class MapperAutoConfiguration implements InitializingBean {

	private final SqlSessionFactory sqlSessionFactory;

	private final SmartMapperProperties properties;

	public MapperAutoConfiguration(SqlSessionFactory sqlSessionFactory, SmartMapperProperties properties) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.properties = properties;
	}

	/**
	 * 关键步骤,Bean初始化时注册SmartMapper
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		PropertiesConfiguration configuration = properties.createConfiguration();
		MapperTemplateAssistor assistor = new MapperTemplateAssistor(configuration);
		Stream.of(configuration.getMappers()).forEach(assistor::registerMapper);
		assistor.processConfiguration(sqlSessionFactory.getConfiguration());
	}

}
