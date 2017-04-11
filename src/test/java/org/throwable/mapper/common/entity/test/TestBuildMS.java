package org.throwable.mapper.common.entity.test;

import com.google.common.collect.Lists;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.ibatis.mapping.SqlCommandType.SELECT;
import static org.apache.ibatis.mapping.SqlCommandType.UPDATE;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/11 12:04
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBuildMS {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void testCreateDynamicMappedStatement() throws Exception {
		Configuration config = sqlSessionFactory.getConfiguration();
		SqlNode rootSqlNode = new TextSqlNode("UPDATE ${dynamicTable} SET NAME = #{user.name} WHERE ID = #{user.id}");
		SqlSource sqlSource = new DynamicSqlSource(config, rootSqlNode);
		//新建MappedStatement
		MappedStatement.Builder statementBuilder = new MappedStatement.Builder(config, "DynamicSqlSource", sqlSource, UPDATE);
		statementBuilder.resource("DynamicSqlSource");
		statementBuilder.fetchSize(null);
		statementBuilder.keyGenerator(new NoKeyGenerator());
		statementBuilder.keyProperty("id");
		statementBuilder.keyColumn(null);
		statementBuilder.databaseId(null);
		statementBuilder.lang(config.getDefaultScriptingLanguageInstance());
		statementBuilder.resultOrdered(false);
		statementBuilder.resultSets(null);
		statementBuilder.timeout(config.getDefaultStatementTimeout());
		//新建ParameterMap
		ParameterMap inlineParameterMap = new ParameterMap.Builder(config, "DynamicSqlSource-ParameterMap-Inline", Map.class,
				newArrayList()).build();
		statementBuilder.parameterMap(inlineParameterMap);
		//新建ResultMap
		ResultMap inlineResultMap = new ResultMap.Builder(config, "DynamicSqlSource-ResultMap-Inline", Integer.class,
				newArrayList(), null).build();
		statementBuilder.resultMaps(Lists.newArrayList(inlineResultMap));
		statementBuilder.resultSetType(null);
		statementBuilder.flushCacheRequired(false);
		statementBuilder.useCache(false);
		statementBuilder.cache(null);
		//添加MappedStatement到Configuration
		config.addMappedStatement(statementBuilder.build());
	}

	@Test
	public void testDynamicMappedStatement() throws Exception {
		User user = new User();
		user.setName("ppmoney");
		user.setId("sdsad");
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("dynamicTable", "User");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			sqlSession.update("DynamicSqlSource", map);
			sqlSession.commit();
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
	}
}
