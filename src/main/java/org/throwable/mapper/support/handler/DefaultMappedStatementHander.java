package org.throwable.mapper.support.handler;

import com.google.common.collect.Lists;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.throwable.mapper.common.constant.SqlSourceEnum;

import java.util.List;

import static org.apache.ibatis.mapping.SqlCommandType.UPDATE;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/11 13:05
 */
@Component
public class DefaultMappedStatementHander extends AbstractMappedStatementBuilder {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	protected SqlSource createCustomSqlSource(Configuration configuration) {
		return null;
	}

	@Override
	protected SqlSource createDynamicSqlSource(Configuration configuration, SqlNode sqlNode) {
		return new DynamicSqlSource(configuration, sqlNode);
	}

	@Override
	protected SqlSource createRawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
		return new RawSqlSource(configuration, rootSqlNode, parameterType);
	}

	@Override
	protected SqlSource createRawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
		return new RawSqlSource(configuration, sql, parameterType);
	}

	@Override
	protected SqlSource createStaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
		return new StaticSqlSource(configuration, sql, parameterMappings);
	}

	@Override
	protected MappedStatement.Builder createMappedStatementBuilder(Configuration configuration, String msId,
																   SqlSource sqlSource, SqlCommandType sqlCommandType) {
		return  new MappedStatement.Builder(configuration, msId, sqlSource, sqlCommandType);
	}

	@Override
	protected ParameterMap createParameterMap(Configuration configuration, String pmId, Class<?> type,
											  List<ParameterMapping> parameterMappings) {
		return new ParameterMap.Builder(configuration, pmId, type, parameterMappings).build();
	}

	@Override
	protected ResultMap createResultMap(Configuration configuration, String rmid, Class<?> type,
										List<ResultMapping> resultMappings, Boolean autoMapping) {
		return new ResultMap.Builder(configuration, rmid, type, resultMappings, autoMapping).build();
	}

	public void addMappedStatementToConfiguration(SqlSourceEnum sqlSourceEnum,
												  SqlNode sqlNode,
												  String sql,
												  Class<?> parameterType,
												  List<ParameterMapping> parameterMappings,
												  String mappedStatementId,
												  SqlCommandType sqlCommandType,
												  String parameterMapId,
												  Class<?> parameterMapClazz,
												  String resultMapId,
												  Class<?> resultMapClazz,
												  List<ResultMapping> resultMappings,
												  Boolean autoMappings,
												  String resource,
												  String keyProperty,
												  String keyColumn,
												  KeyGenerator keyGenerator) {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		SqlSource sqlSource = createSqlSource(sqlSourceEnum, configuration, sqlNode, sql, parameterType, parameterMappings);

		MappedStatement.Builder statementBuilder = createMappedStatementBuilder(configuration, mappedStatementId, sqlSource, sqlCommandType);

		ParameterMap parameterMap = createParameterMap(configuration, parameterMapId, parameterMapClazz, parameterMappings);
		statementBuilder.parameterMap(parameterMap);

		ResultMap resultMap = createResultMap(configuration, resultMapId, resultMapClazz, resultMappings, autoMappings);
		statementBuilder.resultMaps(Lists.newArrayList(resultMap));

		statementBuilder.resource(resource);
		statementBuilder.keyGenerator(keyGenerator);
		statementBuilder.keyProperty(keyProperty);
		statementBuilder.keyColumn(keyColumn);
		statementBuilder.fetchSize(null);
		statementBuilder.databaseId(null);
		statementBuilder.lang(configuration.getDefaultScriptingLanguageInstance());
		statementBuilder.resultOrdered(false);
		statementBuilder.resultSets(null);
		statementBuilder.timeout(configuration.getDefaultStatementTimeout());
		statementBuilder.resultSetType(null);
		statementBuilder.flushCacheRequired(false);
		statementBuilder.useCache(false);
		statementBuilder.cache(null);

		configuration.addMappedStatement(statementBuilder.build());
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
