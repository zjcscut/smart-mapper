package org.throwable.mapper.support.provider;

import lombok.val;
import org.apache.ibatis.mapping.MappedStatement;
import org.throwable.mapper.support.assist.MapperTemplateAssistor;
import org.throwable.mapper.support.repository.AbstractMapperTemplate;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 0:17
 */
public class DeleteMapperProvider  extends AbstractMapperTemplate {

	public DeleteMapperProvider(Class<?> mapperClass, MapperTemplateAssistor mapperTemplateAssistor) {
		super(mapperClass, mapperTemplateAssistor);
	}

	public String deleteByPrimaryKey(MappedStatement ms){
		val entityClass = getEntityClass(ms);
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

	public String delete(MappedStatement ms){
		val entityClass = getEntityClass(ms);
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

	public String deleteByCondition(MappedStatement ms){
		val entityClass = getEntityClass(ms);
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

	public String deleteByField(MappedStatement ms){
		val entityClass = getEntityClass(ms);
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}
}
