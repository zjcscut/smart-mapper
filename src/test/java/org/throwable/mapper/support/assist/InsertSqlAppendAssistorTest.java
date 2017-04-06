package org.throwable.mapper.support.assist;

import org.junit.Before;
import org.junit.Test;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.common.entity.test.User;
import org.throwable.mapper.configuration.prop.PropertiesConfiguration;
import org.throwable.mapper.support.filter.impl.IncludeFieldFilter;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/6 12:36
 */
public class InsertSqlAppendAssistorTest {

	@Before
	public void setUp() throws Exception {
		EntityTableAssisor.initEntityNameMap(User.class, new PropertiesConfiguration());
	}

	@Test
	public void insertIntoTable() throws Exception {

		String s = InsertSqlAppendAssistor.insertColumns(User.class, new IncludeFieldFilter("Name,age"), true);
		System.out.println(s);
	}

}