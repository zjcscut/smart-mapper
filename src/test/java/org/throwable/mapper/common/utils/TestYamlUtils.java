package org.throwable.mapper.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.mapper.Application;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 23:07
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestYamlUtils {

	@Test
	public void test1()throws Exception{
//		Map<String, Object> map =  YamlUtils.yaml2Map("application.yaml");
//		if (null != map){
//			for (Map.Entry<String,Object> entry: map.entrySet()){
//				System.out.println(String.format("key = %s,value = %s",entry.getKey(),entry.getValue()));
//			}
//		}
		Properties  p = YamlUtils.yaml2Properties("application.yaml");
		if (p != null){
			Enumeration<?> names=  p.propertyNames();
			while (names.hasMoreElements()){
				Object name = names.nextElement();
				System.out.println(String.format("key = %s,value = %s",name,p.get(name)));
			}
		}

	}
}
