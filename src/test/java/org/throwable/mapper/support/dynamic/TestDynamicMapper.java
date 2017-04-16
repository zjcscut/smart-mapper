package org.throwable.mapper.support.dynamic;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;
import org.throwable.mapper.common.entity.test.User;
import org.throwable.mapper.common.entity.test.UserLong;
import org.throwable.mapper.common.entity.test.mapper.DynamicUserMapper;
import org.throwable.mapper.support.handler.DefaultMappedStatementHander;
import org.throwable.mapper.support.plugins.generator.identity.MultipleJdbc3KeyGenerator;

import java.util.Map;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/16 4:21
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDynamicMapper {

	@Autowired
	private DynamicUserMapper dynamicUserMapper;

	@Autowired
	private DefaultMappedStatementHander defaultMappedStatementHander;

	@Before
	public void setUp() throws Exception {
//		org.throwable.mapper.common.entity.test.mapper.DynamicUserMapper.insertUser
		defaultMappedStatementHander.addScriptSqlMappedStatement(
				"INSERT INTO USER_AUTO_INCREASE(NAME,AGE) VALUES (#{name},#{age})",
				"org.throwable.mapper.common.entity.test.mapper.DynamicUserMapper.insertUser",
				SqlCommandType.INSERT,
				"DynamicSqlSource-ParameterMap-Inline",
				User.class,
				"DynamicSqlSource-ResultMap-Inline",
				Long.class,
				"DynamicSqlSource",
				"id",
				"ID",
				new MultipleJdbc3KeyGenerator());
	}

	@Test
	public void test1() throws Exception {
		UserLong user = new UserLong();
		user.setName("zjc");
		user.setAge(24);
		dynamicUserMapper.insertUser(user);
	}
}
