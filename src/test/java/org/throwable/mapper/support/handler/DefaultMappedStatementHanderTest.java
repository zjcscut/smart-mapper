package org.throwable.mapper.support.handler;

import com.google.common.collect.Lists;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
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


    public void addMappedStatementToConfiguration() throws Exception {
        SqlNode rootSqlNode = new TextSqlNode("UPDATE ${dynamicTable} SET NAME = #{user.name}, SEX = #{user.sex} WHERE ID = #{user.id}");
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
                parameterMappings,
                "DynamicSqlSource-ResultMap-Inline",
                Integer.class,
                resultMappings,
                null,
                "DynamicSqlSource",
                "id",
                "ID",
                new NoKeyGenerator());
    }

    @Test
    public void testDynamicMappedStatement() throws Exception {
        User user = new User();
        user.setName("ppmoney");
        user.setId("uuid1");
        user.setSex("guess");
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


    private SqlSource createDynamicSqlNode() {
        String sql = " UPDATE ${dynamicTable} \n" +
                "<trim prefix=\"set\" suffixOverrides=\",\">\n" +
                "<trim prefix=\"NAME = CASE\" suffix=\"END,\">\n" +
                "<foreach collection=\"records\" item=\"record\">\n" +
                "<if test=\"record.name != null\">\n" +
                "WHEN ID = #{record.id} THEN #{record.name}\n" +
                "</if>\n" +
                "</foreach>\n" +
                "</trim>\n" +
                "<trim prefix=\"AGE = CASE\" suffix=\"END,\">\n" +
                "<foreach collection=\"records\" item=\"record\">\n" +
                "<if test=\"record.age != null\">\n" +
                "WHEN ID = #{record.id} THEN #{record.age}\n" +
                "</if>\n" +
                "</foreach>\n" +
                "</trim>\n" +
                "</trim>\n" +
                "WHERE ID IN\n" +
                "<foreach collection=\"records\" separator=\",\" item=\"record\" open=\"(\" close=\")\">\n" +
                "#{record.id}\n" +
                "</foreach>\n";
        return defaultMappedStatementHander.createScriptSqlSource(defaultMappedStatementHander.getSqlSessionFactory().getConfiguration(),
                "DynamicSqlSource", sql, Map.class);
    }

    @Before
    public void beforeDynamicSqlSource() throws Exception {
        defaultMappedStatementHander.addMappedStatementToConfiguration(
                createDynamicSqlNode(),
                "DynamicSqlSource",
                SqlCommandType.UPDATE,
                "DynamicSqlSource-ParameterMap-Inline",
                Map.class,
                "DynamicSqlSource-ResultMap-Inline",
                Integer.class,
                "DynamicSqlSource",
                "id",
                "ID",
                new NoKeyGenerator());
    }

    @Test
    public void testDynamicSqlSource() throws Exception {
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
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("dynamicTable", "User");
        SqlSession sqlSession = defaultMappedStatementHander.getSqlSessionFactory().openSession();
        try {
            int count = sqlSession.update("DynamicSqlSource", map);
            System.out.println("count : " + count);
            sqlSession.commit();
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
    }


}