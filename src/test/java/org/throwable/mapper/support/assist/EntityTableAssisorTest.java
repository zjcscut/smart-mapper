package org.throwable.mapper.support.assist;

import org.junit.Test;
import org.throwable.mapper.common.entity.User;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/2 14:50
 */
public class EntityTableAssisorTest {
	@Test
	public void getEntityTable() throws Exception {
	}

	@Test
	public void getDefaultOrderByClause() throws Exception {
	}

	@Test
	public void getAllColumns() throws Exception {
	}

	@Test
	public void getPrimaryColumns() throws Exception {
	}

	@Test
	public void getDefaultSelectClause() throws Exception {
	}

	@Test
	public void initEntityNameMap() throws Exception {
		EntityTableAssisor.initEntityNameMap(User.class);
		EntityTableAssisor.getDefaultSelectClause(User.class);

	}

}