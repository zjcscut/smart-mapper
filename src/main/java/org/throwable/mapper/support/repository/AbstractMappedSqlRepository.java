package org.throwable.mapper.support.repository;

import org.throwable.mapper.support.plugins.condition.Condition;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.throwable.mapper.support.assist.UpdateSqlAppendAssistor.*;
import static org.throwable.mapper.support.assist.InsertSqlAppendAssistor.*;
import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/15 13:34
 */
public abstract class AbstractMappedSqlRepository extends CachableMappedSqlRepository {

	public static String createDynamicBatchInsertScriptSql(Class<?> clazz, String msId, boolean isUUID) {
		try {
			return sqlCache.get(msId, () -> {
				StringBuilder sql = new StringBuilder(insertDynamicTable());
				if (isUUID) {
					sql.append(insertBatchColumns(clazz, false));
					sql.append(insertBatchValues(clazz, false));
				} else {
					sql.append(insertBatchColumns(clazz, true));
					sql.append(insertBatchValues(clazz, true));
				}
				return sql.toString();
			});
		} catch (ExecutionException e) {
			//ignore
		}
		throw new IllegalStateException(String.format("load sql from cache failed!,msId:%s", msId));
	}

	public static String createDynamicBatchUpdateScriptSql(Class<?> clazz, String msId, boolean skipNull) {
		try {
			return sqlCache.get(msId, () -> updateDynamicTable() + batchUpdateSetColumns(clazz, skipNull));
		} catch (ExecutionException e) {
			//ignore
		}
		throw new IllegalStateException(String.format("load sql from cache failed!,msId:%s", msId));
	}

	public static String createDynamicUpdateScriptSql(Class<?> clazz, String msId, boolean skipNull) {
		try {
			return sqlCache.get(msId, () -> updateDynamicTable() + dynamicUpdateSetColumns(clazz, skipNull)
					+ buildDynamicUpdateWhereClause(clazz));
		} catch (ExecutionException e) {
			//ignore
		}
		throw new IllegalStateException(String.format("load sql from cache failed!,msId:%s", msId));
	}

	public static String createDynamicUpdateByConditionScriptSql(Class<?> clazz, String msId,
																 boolean skipNull, Condition condition) {
		try {
			return sqlCache.get(msId, () -> {
				Set<String> updateColumnSet = condition.getUpdateColumnSet();
				if (null == updateColumnSet || updateColumnSet.isEmpty()) {
					throw new IllegalArgumentException("update by condition create scriptSql failed,condition must contain update vars");
				}
				return updateDynamicTable() + dynamicUpdateByCondtionSetColumns(clazz, updateColumnSet, skipNull) + conditionWhereClause(PARAM_CONDITION);
			});
		} catch (ExecutionException e) {
			//ignore
		}
		throw new IllegalStateException(String.format("load sql from cache failed!,msId:%s", msId));
	}

}
