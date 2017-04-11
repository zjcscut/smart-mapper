package org.throwable.mapper.support.handler;

import com.google.common.collect.Lists;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
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


	private SqlNode createDynamicSqlNode(){
				" UPDATE ${dynamicTable} \n" +
        "<trim prefix=\"set\" suffixOverrides=\",\">\n" +
            "<trim prefix="NAME = CASE" suffix="END,">\n" +
                <foreach collection="records" item="record">
                    <if test="record.name != null">
				WHEN ID = #{record.id} THEN #{record.name}
                    </if>
                </foreach>
            </trim>
            <trim prefix="AGE = CASE" suffix="END,">
                <foreach collection="records" item="record">
                    <if test="record.age != null">
				WHEN ID = #{record.id} THEN #{record.age}
                    </if>
                </foreach>
            </trim>
        </trim>
				WHERE ID IN
        <foreach collection="records" separator="," item="record" open="(" close=")">
            #{record.id}
        </foreach>
	}



}