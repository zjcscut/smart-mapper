package org.throwable.mapper.support.handler;

import com.google.common.collect.Lists;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;
import org.throwable.mapper.common.constant.SqlSourceEnum;
import org.throwable.mapper.common.entity.test.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/11 13:25
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultMappedStatementHanderTest {

	@Autowired
	private DefaultMappedStatementHander defaultMappedStatementHander;

	@Before
	public void addMappedStatementToConfiguration() throws Exception {
		SqlNode rootSqlNode = new TextSqlNode("UPDATE ${dynamicTable} SET NAME = #{user.name} WHERE ID = #{user.id}");
		List<ParameterMapping> parameterMappings = Lists.newArrayList();
		List<ResultMapping> resultMappings = Lists.newArrayList();
		defaultMappedStatementHander.addMappedStatementToConfiguration(
				SqlSourceEnum.DYNAMIC_SQLSOURCE,
				rootSqlNode,
				null,
				null,
				parameterMappings,
				"DynamicSqlSource",
				SqlCommandType.UPDATE,
				"DynamicSqlSource-ParameterMap-Inline",
				Map.class,
				"DynamicSqlSource-ResultMap-Inline",
				Integer.class,
				resultMappings,
				null,
				"DynamicSqlSource",
				"id",
				"ID",
				null);
	}

	@Test
	public void testDynamicMappedStatement() throws Exception {
		User user = new User();
		user.setName("ppmoney");
		user.setId("sdsad");
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("dynamicTable", "User");
		SqlSession sqlSession = defaultMappedStatementHander.getSqlSessionFactory().openSession();
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