package org.throwable.mapper.common.entity.test;

import com.google.common.collect.Lists;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;
import org.throwable.mapper.BatchExecutorService;
import org.throwable.mapper.common.entity.test.mapper.UserMapper;
import org.throwable.mapper.configuration.MybatisAutoConfiguration;
import org.throwable.mapper.support.context.BeanRegisterHandler;
import org.throwable.mapper.support.filter.impl.IncludeFieldFilter;
import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.plugins.pagination.PageModel;
import org.throwable.mapper.support.plugins.pagination.Pager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private BatchExecutorService batchExecutorService;

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
		System.out.println(user.getId());
	}

	@Test
	public void Test3()throws Exception{
		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setAge(25);
		user1.setName("ppzzzzz");
		user1.setBirth(new Date());
		user1.setSex("MAN");
        users.add(user1);
		User user2 = new User();
		user2.setAge(256);
		user2.setName("ppzzzzssdaz");
		user2.setBirth(new Date());
		user2.setSex("MAN");
		users.add(user2);
		userMapper.batchInsert(users);
		System.out.println(user1.getId());
		System.out.println(user2.getId());
	}

	@Test
	public void testCondition()throws Exception{
		Condition condition = Condition.create(User.class);
		condition.gt("id",0).like("name","%pp%").desc("id").or("name","like","%z%");
		List<User> users = userMapper.selectByCondition(condition);
		assertNotNull(users);
		for (User u : users){
			System.out.println(u);
		}
		long count = userMapper.countByCondition(condition);
		System.out.println(count);
		PageModel<User> userPage = userMapper.selectByConditionPage(condition,new Pager(1,10));
		assertNotNull(userPage);
	}

	@Test
	public void testUpdate()throws Exception{
		User user1 = new User();
		user1.setAge(251);
		user1.setSex("sdasdasd");
		user1.setName("你好好好");
		List<User> list = Lists.newArrayList();
		list.add(user1);
		int count = userMapper.batchUpdate(list);
		System.out.println(count);

	}

	@Test
	public void testInsertUUId()throws Exception{
		User user = new User();
		user.setSex("MAN");
		user.setBirth(new Date());
		user.setName("zjc");
		user.setAge(24);
		userMapper.insertNoneSkipPrimaryKey(user);
//		userMapper.insertDynamicKey(user);
		System.out.println(user.getId());
	}


	@Test
	public void testBatchInsertUUId()throws Exception{
		User user = new User();
		user.setSex("MAN");
		user.setBirth(new Date());
		user.setName("zjc");
		user.setAge(24);
        List<User> users = Lists.newArrayList();
        users.add(user);
		User user1 = new User();
		user1.setSex("WOMAN");
		user1.setBirth(new Date());
		user1.setName("aaaaa");
		user1.setAge(242);
		users.add(user1);
		userMapper.batchInsert(users);
		users.forEach(a->System.out.println(a.getId()));
	}

	@Test
	public void testBatchExecutorService()throws Exception{
//		System.out.println(batchExecutorService.executeBatchInsert());
	}

}
