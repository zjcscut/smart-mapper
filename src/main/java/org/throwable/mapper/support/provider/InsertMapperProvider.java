package org.throwable.mapper.support.provider;

import lombok.val;
import org.apache.ibatis.mapping.MappedStatement;
import org.throwable.mapper.support.assist.MapperTemplateAssistor;
import org.throwable.mapper.support.repository.AbstractMapperTemplate;

import static org.throwable.mapper.support.assist.SqlAppendAssistor.*;
import static org.throwable.mapper.support.assist.InsertSqlAppendAssistor.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 0:02
 */
public class InsertMapperProvider extends AbstractMapperTemplate {

	public InsertMapperProvider(Class<?> mapperClass, MapperTemplateAssistor mapperTemplateAssistor) {
		super(mapperClass, mapperTemplateAssistor);
	}

	public String insert(MappedStatement ms) {
		val entityClass = getEntityClass(ms);
		//主键回写
		getIdentityColumn(entityClass).ifPresent(column -> newSelectKeyMappedStatement(ms, column));
		//拼接动态SQL
		StringBuilder builder = new StringBuilder(checkDefaultParamValue());
		builder.append(insertUniqueId(entityClass, getUUID()));
		builder.append(insertIntoTable(entityClass, tableName(entityClass)));
		builder.append(insertColumns(entityClass,null,true));
		builder.append(insertValues(entityClass));
		return builder.toString();
	}
}
