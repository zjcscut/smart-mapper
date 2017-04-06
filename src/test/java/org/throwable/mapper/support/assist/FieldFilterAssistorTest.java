package org.throwable.mapper.support.assist;

import org.junit.Test;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.common.entity.test.User;
import org.throwable.mapper.configuration.prop.PropertiesConfiguration;
import org.throwable.mapper.support.filter.impl.ExcludeFieldFilter;
import org.throwable.mapper.support.filter.impl.IncludeFieldFilter;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/6 12:01
 */
public class FieldFilterAssistorTest {

	@Test
	public void testFilter()throws Exception{
		EntityTableAssisor.initEntityNameMap(User.class,new PropertiesConfiguration());
		Set<EntityColumn>  sets = EntityTableAssisor.getEntityTable(User.class).getEntityClassColumns();
		sets = FieldFilterAssistor.filter(sets,new ExcludeFieldFilter("Name,birth"));
		assertNotNull(sets);
	}

}