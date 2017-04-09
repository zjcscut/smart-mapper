package org.throwable.mapper.support.assist;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 2:02
 */
public abstract class DeleteSqlAppendAssistor extends ConditionSqlAppendAssistor {

	public static String deleteFromTable(Class<?> entityClass, String defaultTableName) {
		return "DELETE FROM " + getDynamicTableName(entityClass, defaultTableName) + " ";
	}

}
