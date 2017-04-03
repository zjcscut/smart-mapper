package org.throwable.mapper.support.repository;

import org.junit.Test;
import org.throwable.mapper.common.constant.NameStyleEnum;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/30 23:19
 */
public class NameStyleHandlerFactoryTest {

	@Test
	public void singleTon() throws Exception {
		NameStyleHandlerFactory factory1 = NameStyleHandlerFactory.getInstance();
		NameStyleHandlerFactory factory2 = NameStyleHandlerFactory.getInstance();
		assertEquals(factory1, factory2);
	}

	@Test
	public void create() throws Exception {
		String result = NameStyleHandlerFactory
				.getInstance()
				.create(NameStyleEnum.CAMELCASE_TO_UNDERLINE)
				.contert("customerId");
		assertEquals(result, "customer_id");
	}

}