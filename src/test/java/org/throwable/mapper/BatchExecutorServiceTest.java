package org.throwable.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.common.entity.test.User;
import org.throwable.mapper.common.entity.test.UserLong;
import org.throwable.mapper.configuration.prop.PropertiesConfiguration;
import org.throwable.mapper.support.assist.EntityTableAssisor;
import org.throwable.mapper.support.filter.impl.IncludeFieldFilter;
import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.plugins.pagination.PageModel;
import org.throwable.mapper.support.plugins.pagination.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Slf4j
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
        batchExecutorService.executeBatchInsert(records, 10);
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

    @Test
    public void executeUpdate() throws Exception {
        UserLong user = new UserLong();
        user.setId(1L);
        user.setName("zjcscut1");
        user.setAge(26);
        long start = System.currentTimeMillis();
        batchExecutorService.update(user, true);
        log.error("cost time:" + (System.currentTimeMillis() - start) + " ms");
        System.out.println("cost time:" + (System.currentTimeMillis() - start) + " ms");

        long start2 = System.currentTimeMillis();
        batchExecutorService.update(user, true);
        log.error("cost time 2:" + (System.currentTimeMillis() - start2) + " ms");
        System.out.println("cost time 2:" + (System.currentTimeMillis() - start2) + " ms");

    }

    @Test
    public void executeUpdateCondtion() throws Exception {
        Condition condition = Condition.create(UserLong.class);
        condition.and("id", "=", 1L).forceMode(true);
        Map<String, Object> vars = new HashMap<>();
        vars.put("Name", "zjc-condition");
        vars.put("age", 2222222);
        condition.setVars(vars);
        batchExecutorService.updateByCondition(condition, true);
    }

    @Test
    public void executeSelectOneCondition() throws Exception {
        Condition condition = Condition.create(User.class);
        condition.and("id", "=", "uuid1");
        long start = System.currentTimeMillis();
        User user = batchExecutorService.selectOneByCondition(condition);
        System.out.println("cost time1:" + (System.currentTimeMillis() - start) + " ms");
        assertNotNull(user);
        start = System.currentTimeMillis();
        User user1 = batchExecutorService.selectOneByCondition(condition);
        System.out.println("cost time2:" + (System.currentTimeMillis() - start) + " ms");
        assertNotNull(user1);
    }

    @Test
    public void executeSelectListCondition() throws Exception {
        Condition condition = Condition.create(User.class);
        condition.and("id", "IN", "uuid1,uuid2");
        List<User> list = batchExecutorService.selectListByCondition(condition);
        assertNotNull(list);
    }

    @Test
    public void executeCountByCondition() throws Exception {
        Condition condition = Condition.create(User.class);
        condition.and("id", "IN", "uuid1,uuid2");
        long count = batchExecutorService.countByCondition(condition);
        assertEquals(count, 2L);
    }

    @Test
    public void executeSelectPageByCondition() throws Exception {
        Condition condition = Condition.create(User.class);
        condition.and("id", "IN", "uuid1,uuid2");
        PageModel<User> user = batchExecutorService.selectListByConditionPage(condition, new Pager(1, 10));
        assertNotNull(user);
    }
}