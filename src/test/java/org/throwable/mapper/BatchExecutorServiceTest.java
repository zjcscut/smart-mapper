package org.throwable.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.common.entity.test.User;
import org.throwable.mapper.common.entity.test.UserLong;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/14 0:40
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchExecutorServiceTest {

	@Autowired
	private BatchExecutorService batchExecutorService;

	@Test
	public void executeBatchUpdate() throws Exception {
		List<User> records = newArrayList();
		User user = new User();
		user.setName("pp@111");
		user.setId("uuid1");
		user.setAge(111);
		records.add(user);
		User user1 = new User();
		user1.setName("pp@222");
		user1.setId("uuid2");
		user1.setAge(222);
		records.add(user1);
		batchExecutorService.executeBatchUpdate(records);

	}

	@Test
	public void executeBatchInsert() throws Exception {
		List<User> records = newArrayList();
		User user = new User();
		user.setName("pp@111");
		user.setAge(111);
		records.add(user);
		User user1 = new User();
		user1.setName("pp@222");
		user1.setAge(222);
		records.add(user1);
		batchExecutorService.executeBatchInsert(records,10);
	}

	@Test
	public void executeBatchInsertIdGenerator() throws Exception {
		List<UserLong> records = newArrayList();
		UserLong user = new UserLong();
		user.setName("pp@11111");
		user.setAge(11111);
		records.add(user);
		UserLong user1 = new UserLong();
		user1.setName("pp@22222");
		user1.setAge(22222);
		records.add(user1);
		batchExecutorService.executeBatchInsert(records);
	}


}