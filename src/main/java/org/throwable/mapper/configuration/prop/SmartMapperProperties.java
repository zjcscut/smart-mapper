package org.throwable.mapper.configuration.prop;

import lombok.Data;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
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
		if (this.mapperLocations != null) {
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


	public PropertiesConfiguration createConfiguration(){

		return new PropertiesConfiguration();
	}
}
