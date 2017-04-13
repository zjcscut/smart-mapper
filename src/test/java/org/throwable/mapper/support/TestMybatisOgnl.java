package org.throwable.mapper.support;

import org.junit.Test;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/14 1:33
 */
public class TestMybatisOgnl {


	@Test
	public void test1()throws Exception{
		System.out.println(org.apache.ibatis.ognl.Ognl.getValue("@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")",null));
	}
}
