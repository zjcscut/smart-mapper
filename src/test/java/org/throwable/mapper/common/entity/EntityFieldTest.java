package org.throwable.mapper.common.entity;

import org.junit.Test;
import org.throwable.mapper.utils.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/1 0:33
 */
public class EntityFieldTest {

	@Test
	public void testField()throws Exception{
		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors){
			if (descriptor.getName().equals("class")){
				continue;
			}
			System.out.println(String.format("name : %s,readMedhod :%s,writeMethod : %s,javaType : %s",
					descriptor.getName(),
					descriptor.getReadMethod().getName(),
					descriptor.getWriteMethod().getName(),
					descriptor.getPropertyType().getCanonicalName()));
		}
	}
	@Test
	public void testRefletionFields()throws Exception{
		List<Field> fields = ReflectionUtils.getDeclaredFields(User.class);
		assertNotNull(fields);
	}

}