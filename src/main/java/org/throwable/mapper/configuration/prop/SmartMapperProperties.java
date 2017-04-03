package org.throwable.mapper.configuration.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:25
 */
@Data
@ConfigurationProperties(prefix = "smart.mapper")
public class SmartMapperProperties {



	public PropertiesConfiguration createConfiguration(){

		return null;
	}
}
