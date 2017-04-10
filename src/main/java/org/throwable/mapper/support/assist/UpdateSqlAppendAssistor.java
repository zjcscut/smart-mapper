package org.throwable.mapper.support.assist;

import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.common.entity.EntityTable;

import java.util.Set;

import static java.lang.String.format;
import static org.throwable.mapper.common.constant.CommonConstants.*;
import static org.throwable.mapper.utils.OGNL.CHECK_FOR_NULL;
import static org.throwable.mapper.support.assist.EntityTableAssisor.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/7 18:26
 */
public abstract class UpdateSqlAppendAssistor extends ConditionSqlAppendAssistor {

    public static String checkPrimaryKeyValue(Class<?> entityClass, String parameterName) {
        return getPrimaryColumns(entityClass).stream()
                .map(column -> {
                    String ognlParameter = getEntityPrefix(parameterName) + column.getProperty();
                    String exp = format(CHECK_FOR_NULL, ognlParameter, "'id must not be null for update'");
                    return "<if test=\"" + exp + "\"/>\n";
                })
                .reduce(String::concat)
                .orElse("");
    }

//	public static String updateSetColumns(Class<?> entityClass) {
//		return "<set>\n" +
//				"<if test=\"condition neq null and @org.throwable.mapper.utils.OGNL@hasSelectColumns(condition)\">\n" +
//				"<if test=\"condition neq null and condition.selectColumns neq null\">\n" +
//				"\n<foreach collection=\"condition.selectColumns\" item=\"selectColumn\" separator=\",\">\n" +
//				"${@org.throwable.mapper.support.assist.UpdateSqlAppendAssistor@getColumnSetPairsFromFilter(condition.entityTable,selectColumn,allowUpdateToNull)}\n" +
//				"\n</foreach>\n" +
//				"</if>\n" +
//				"</if>\n" +
//				"<if test=\"condition eq null or @org.throwable.mapper.utils.OGNL@hasNotSelectColumns(condition)\">\n" +
//				getColumnSetPairs(entityClass) +
//				"</if>\n" +
//				"</set>\n";
//	}

    public static String updateSetColumns(Class<?> entityClass) {
        return "<set>\n" +
                getColumnSetPairs(entityClass) +
                "</set>\n";
    }

    private static String getColumnSetPairs(Class<?> entityClass) {
        return getAllColumns(entityClass).stream()
                .filter(column -> !column.isIdentity() && column.isUpdatable() && !column.isUUID())
                .map(UpdateSqlAppendAssistor::getColumnPairHolder)
                .reduce(String::concat)
                .orElse("");
    }

    private static String getColumnPairHolder(EntityColumn column) {
        String content = column.getColumnEqualsHolder(PARAM_RECORD).concat(", ");
        if (isNotNull(column)) {
            return getIfNotNull(PARAM_RECORD, column, content);
        } else {
            return getIfAllowUpdateToNull(column, content);
        }
    }

    public static String updateTable(Class<?> entityClass, String defaultTableName, String parameterName) {
        return "UPDATE " + getDynamicTableName(entityClass, defaultTableName, parameterName) + " ";
    }

    private static String getIfAllowUpdateToNull(EntityColumn column, String content) {
        String template = "<choose>\n" +
                "<when test='allowUpdateToNull'>\n" +
                "%s" +
                "\n</when>\n" +
                "<otherwise>\n" +
                "<if test='%s != null'>\n" +
                "%s" +
                "\n</if>\n" +
                "</otherwise>\n" +
                "</choose>\n";
        return format(template, content, PARAM_RECORD.concat(".").concat(column.getProperty()), content);
    }

    public static String primaryKeyWhereClause(Class<?> entityClass) {
        return primaryKeyWhereClause(entityClass, PARAM_DEFAULT);
    }

    public static String primaryKeyWhereClause(Class<?> entityClass, String parameterName) {
        final StringBuilder builder = new StringBuilder();
        builder.append("<where>\n");
        getPrimaryColumns(entityClass).forEach(column -> builder.append(" AND ").append(getColumnEqualsHolder(parameterName.concat("."), column)));
        builder.append("\n</where>\n");
        return builder.toString();
    }

    /**
     * 这个方法暂时没想到怎么写,有问题,别用
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static String getColumnSetPairsFromFilter(Object entityTable, Object selectColunm, Object allowUpdateToNull) {
        if (entityTable instanceof EntityTable && selectColunm instanceof String && allowUpdateToNull instanceof Boolean) {
            EntityTable table = (EntityTable) entityTable;
            String column = (String) selectColunm;
            Boolean enableUpdateNull = (Boolean) allowUpdateToNull;
            EntityColumn target = table.getEntityClassColumns()
                    .stream()
                    .filter(a -> a.isUpdatable() && !a.isUUID() && !a.isIdentity())
                    .findFirst()
                    .orElse(null);
            return getColumnPairHolder(target);
        } else {
            throw new IllegalArgumentException("getColumnSetPairsFromFilter fialed!");
        }
    }

    public static String batchUpdateSetColumns(Class<?> entityClass) {
        return "\n<set>\n" +
                "<trim suffixOverrides=\",\" prefixOverrides=\",\">\n" +
                getBatchUpdateColumnSetPairs(entityClass) +
                "</trim>\n" +
                "</set>\n";
    }

    private static String getBatchUpdateColumnSetPairs(Class<?> entityClass) {
        EntityColumn key = getPrimaryColumn(entityClass);
        Set<EntityColumn> noneKeyColumns = getNonePrimaryColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        noneKeyColumns.forEach(a -> sql.append(buildBatchUpdateColumnSetPair(key, a)));
        sql.append(buildBatchUpdateByPrimaryKeyWhereClause(key));
        return sql.toString();
    }

    private static String buildBatchUpdateColumnSetPair(EntityColumn key, EntityColumn target) {
        return "," +
                target.getColumn().concat(" = ") +
                "\n<foreach collection=\"" + PARAM_RECORDS + "\" item=\"item\" separator=\" \" open=\"" + "CASE " + key.getColumn() + "\" close=\"end\">\n" +
                " WHEN " +
                getColumnHolder("item.", key) +
                " THEN " +
                getColumnHolder("item.", target) +
                "\n</foreach>\n";
    }

    private static String buildBatchUpdateByPrimaryKeyWhereClause(EntityColumn key) {
        return "<where>\n" +
                key.getColumn().concat(" IN ") +
                "\n<foreach collection=\"" + PARAM_RECORDS + "\" item=\"item\" separator=\",\" open=\"(\" close=\")\">\n" +
                getColumnHolder("item.", key) +
                "\n</foreach>\n" +
                "</where>\n";
    }
}
