package org.throwable.mapper.support.provider;

import org.apache.ibatis.mapping.MappedStatement;
import org.throwable.mapper.support.assist.MapperTemplateAssistor;
import org.throwable.mapper.support.repository.AbstractMapperTemplate;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 0:17
 */
public class SelectMapperProvider extends AbstractMapperTemplate{

	public SelectMapperProvider(Class<?> mapperClass, MapperTemplateAssistor mapperTemplateAssistor) {
		super(mapperClass, mapperTemplateAssistor);
	}

	public String selectCondition(MappedStatement ms){

		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

	public String countCondition(MappedStatement ms){

		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}
}
