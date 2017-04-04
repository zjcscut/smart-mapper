package org.throwable.mapper.support.assist;

import org.junit.Test;
import org.throwable.mapper.common.entity.EntityField;
import org.throwable.mapper.common.entity.test.User;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/2 12:45
 */
public class EntityFieldAssistorTest {
	@Test
	public void getEntityFields() throws Exception {
	}

	@Test
	public void getEntityProperties() throws Exception {
		List<EntityField> entityFields = EntityFieldAssistor.getEntityFieldsProperties(User.class);
		assertNotNull(entityFields);
	}

}