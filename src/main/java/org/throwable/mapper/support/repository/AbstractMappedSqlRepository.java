package org.throwable.mapper.support.repository;

import java.util.concurrent.ExecutionException;

import static org.throwable.mapper.support.assist.UpdateSqlAppendAssistor.*;
import static org.throwable.mapper.support.assist.InsertSqlAppendAssistor.*;

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

    public static String createDynamicBatchUpdateScriptSql(Class<?> clazz, String msId,boolean skipNull) {
        try {
            return sqlCache.get(msId, () -> updateDynamicTable() + batchUpdateSetColumns(clazz));
        } catch (ExecutionException e) {
            //ignore
        }
        throw new IllegalStateException(String.format("load sql from cache failed!,msId:%s", msId));
    }

}
