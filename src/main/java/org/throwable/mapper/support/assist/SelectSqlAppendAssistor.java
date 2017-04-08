package org.throwable.mapper.support.assist;

import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.support.filter.FieldFilter;

import java.util.Set;

import static org.throwable.mapper.common.constant.CommonConstants.PARAM_DEFAULT;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 2:02
 */
public abstract class SelectSqlAppendAssistor extends ConditionSqlAppendAssistor {


	public static String selectCountStart(){
		return "SELECT COUNT(*) ";
	}

	/**
	 * select指定列，支持distinct查询
	 */
	public static String selectColumnsByCondition(Class<?> entityClass) {
		//不支持指定列的时候查询全部列
		return "SELECT <if test=\"condition neq null and condition.isDistinct\">DISTINCT</if>" +
				"\n" +
				"<if test=\"condition neq null and @org.throwable.mapper.utils.OGNL@hasSelectColumns(condition)\">" +
				"\n" +
				"  <foreach collection=\"condition.selectColumns\" item=\"selectColumn\" separator=\",\">" +
				"\n" +
				"    ${selectColumn}" +
				"\n" +
				"  </foreach>" +
				"\n" +
				"</if>" +
				"\n" +
				"<if test=\"condition eq null or @org.throwable.mapper.utils.OGNL@hasNotSelectColumns(condition)\">" +
				getSelectColumnsClause(entityClass) +
				"</if>" +
				"\n";
	}


	/**
	 * from tableName - 动态表名
	 *
	 * @param entityClass      实体类
	 * @param defaultTableName 默认表名
	 */
	public static String fromTable(Class<?> entityClass, String defaultTableName) {
		return " FROM " + getDynamicTableName(entityClass, defaultTableName) + " \n";
	}

}
