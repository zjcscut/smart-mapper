package org.throwable.mapper.common.utils;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 23:06
 */
public final class YamlUtils {


	public static Map<String, Object> yaml2Map(String yamlSource) {
		try {
			YamlMapFactoryBean yaml = new YamlMapFactoryBean();
			yaml.setResources(new ClassPathResource(yamlSource));
			return yaml.getObject();
		} catch (Exception e) {
			return null;
		}
	}

	public static Properties yaml2Properties(String yamlSource) {
		try {
			YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
			yaml.setResources(new ClassPathResource(yamlSource));
			return yaml.getObject();
		} catch (Exception e) {
			return null;
		}
	}
}
