package org.throwable.mapper.configuration.prop;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.assertj.core.util.Lists;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:25
 */
@Data
@Slf4j
@NoArgsConstructor
@ConfigurationProperties(prefix = SmartMapperProperties.MYBATIS_PREFIX)
public class SmartMapperProperties {

	public static final String MYBATIS_PREFIX = "smart-mapper";

	/**
	 * Config file path.
	 */
	private String configLocation;

	/**
	 * Location of mybatis mapper files.
	 */
	private String[] mapperLocations;

	/**
	 * Package to scan domain objects.
	 */
	private String typeAliasesPackage;

	/**
	 * Package to scan handlers.
	 */
	private String typeHandlersPackage;

	/**
	 * Check the config file exists.
	 */
	private boolean checkConfigLocation = false;

	/**
	 * Execution mode for {@link org.mybatis.spring.SqlSessionTemplate}.
	 */
	private ExecutorType executorType;

	/**
	 * Externalized properties for configuration.
	 */
	private Properties configurationProperties;

	/**
	 * A Configuration object for customize default settings. If {@link #configLocation}
	 * is specified, this property is not used.
	 */
	@NestedConfigurationProperty
	private Configuration configuration;

	public Resource[] resolveMapperLocations() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		List<Resource> resources = new ArrayList<>();
		if (null != this.mapperLocations) {
			for (String mapperLocation : this.mapperLocations) {
				try {
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}

	/**
	 * 需要扫描的mapper所在的包
	 */
	@NotNull
	private String[] basePackages = {};

	/**
	 * mapper识别注解
	 */
	@NotNull
	private Class<? extends Annotation> annotationClass = Mapper.class;

	/**
	 * MapperFactoryBean的子类,提供重写的入口
	 */
	@NotNull
	private Class<? extends MapperFactoryBean> factoryBean = MapperFactoryBean.class;

	@SuppressWarnings("unchecked")
	public SmartMapperProperties(Environment env) {
		String packagesKey = "smart-mapper.base-packages";
		int index = 0;
		List<String> packages = Lists.newArrayList();
		while (null != env.getProperty(packagesKey + "[" + index + "]")) {
			packages.add(env.getProperty(packagesKey + "[" + index + "]"));
			index++;
		}
		if (packages.size() > 0) {
			basePackages = packages.toArray(new String[packages.size()]);
		}
		String clazz = env.getProperty("smart-mapper.annotation-class", String.class);
		if (StringUtils.isNotBlank(clazz)) {
			try {
				Class<?> annotationClazz = Class.forName(clazz);
				if (Annotation.class.isAssignableFrom(annotationClazz)) {
					this.annotationClass = (Class<? extends Annotation>) annotationClazz;
				}
			} catch (ClassNotFoundException e) {
				log.error("load annotationClass fail", e);
			}
		}
		String bean = env.getProperty("smart-mapper.factory-bean", String.class);
		if (StringUtils.isNotBlank(bean)) {
			try {
				Class<?> beanClazz = Class.forName(bean);
				if (MapperFactoryBean.class.isAssignableFrom(beanClazz)) {
					this.factoryBean = (Class<? extends MapperFactoryBean>) beanClazz;
				}
			} catch (ClassNotFoundException e) {
				log.error("load MapperFactoryBean fail", e);
			}
		}
	}

	public PropertiesConfiguration createConfiguration() {

		return new PropertiesConfiguration();
	}
}
