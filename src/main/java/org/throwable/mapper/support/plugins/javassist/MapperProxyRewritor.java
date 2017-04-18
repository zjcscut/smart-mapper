package org.throwable.mapper.support.plugins.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/17 2:30
 */
public final class MapperProxyRewritor {

	public static void rewriteMapperProxy()throws Exception{
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.apache.ibatis.binding.MapperProxy");
		CtMethod method = clazz.getDeclaredMethod("invoke");
		method.insertBefore("System.out.println($1);System.out.println($2)");
		clazz.writeFile();
	}
}
