package org.throwable.mapper.common.entity.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;
import org.throwable.mapper.common.entity.test.mapper.UserMapper;
import org.throwable.mapper.configuration.MybatisAutoConfiguration;
import org.throwable.mapper.support.context.BeanRegisterHandler;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 2:22
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMapper {

	@Autowired
	@Qualifier(value = "defaultBeanRegisterHandler")
	private BeanRegisterHandler beanRegisterHandler;

	@Autowired
	private UserMapper userMapper;

	@Test
	public void Test1() throws Exception {
		beanRegisterHandler.loadBeanFromContext(MybatisAutoConfiguration.class);
	}

	@Test
	public void Test2() throws Exception {
		User user = new User();
		user.setAge(25);
		user.setName("pp");
		user.setBirth(new Date());
		user.setSex("MAN");
		userMapper.insert(user);
		assertNotNull(user);
		assertEquals(3L, user.getId().longValue());
	}
}
