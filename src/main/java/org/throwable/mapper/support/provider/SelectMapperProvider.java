package org.throwable.mapper.support.provider;

import lombok.val;
import org.apache.ibatis.mapping.MappedStatement;
import org.throwable.mapper.support.assist.MapperTemplateAssistor;

import static org.throwable.mapper.support.assist.SelectSqlAppendAssistor.*;
import static org.throwable.mapper.common.constant.CommonConstants.PARAM_CONDITION;

import org.throwable.mapper.support.repository.AbstractMapperTemplate;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 0:17
 */
public class SelectMapperProvider extends AbstractMapperTemplate {

	public SelectMapperProvider(Class<?> mapperClass, MapperTemplateAssistor mapperTemplateAssistor) {
		super(mapperClass, mapperTemplateAssistor);
	}

	public String selectCondition(MappedStatement ms) {
		val entityClass = getEntityClass(ms);
		setResultType(ms, entityClass);
		StringBuilder builder = new StringBuilder();
		builder.append(checkParamValue(PARAM_CONDITION));
		builder.append(selectColumnsByCondition(entityClass));
		builder.append(fromTable(entityClass, tableName(entityClass)));
		builder.append(conditionWhereClause(PARAM_CONDITION));
		return builder.toString();
	}

	public String countCondition(MappedStatement ms) {

		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}
}
