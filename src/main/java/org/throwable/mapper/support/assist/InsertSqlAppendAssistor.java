package org.throwable.mapper.support.assist;

import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.exception.UnsupportedElementException;
import org.throwable.mapper.support.filter.FieldFilter;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.throwable.mapper.common.constant.CommonConstants.PARAM_DEFAULT;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 2:02
 */
public abstract class InsertSqlAppendAssistor extends SqlAppendAssistor {


	public static Optional<EntityColumn> getIdentityColumn(final Class<?> entityClass) {
		Set<EntityColumn> columnList = EntityTableAssisor.getPrimaryColumns(entityClass).stream()
				.filter(EntityColumn::isInsertable)
				.filter(EntityColumn::isIdentity)
				.collect(toSet());
		if (columnList.size() == 0) {
			return Optional.empty();
		}
		if (columnList.size() > 1) {
			throw new UnsupportedElementException("Too many identity columns defined in " + entityClass.getCanonicalName());
		}
		return Optional.of(columnList.iterator().next());
	}

	/**
	 * 获取预设UUID的动态SQL代码
	 *
	 * @param entityClass        实体类
	 * @param uniqueIdExpression 获取唯一ID的表达式
	 */
	public static String insertUniqueId(final Class<?> entityClass, final String uniqueIdExpression) {
		return EntityTableAssisor.getAllColumns(entityClass).stream()
				.filter(EntityColumn::isInsertable)
				.filter(EntityColumn::isUUID)
				.map(column -> _getUniqueIdSeg(column, PARAM_DEFAULT, uniqueIdExpression))
				.reduce(String::concat)
				.orElse("");
	}


	/**
	 * insert into tableName - 动态表名
	 *
	 * @param entityClass      实体类
	 * @param defaultTableName 默认表名
	 */
	public static String insertIntoTable(Class<?> entityClass, String defaultTableName) {
		return "INSERT INTO " + getDynamicTableName(entityClass, defaultTableName) + " ";
	}

	/**
	 * insert into tableName
	 *
	 * @param tableName 表名
	 */
	public static String insertBatchIntoTable(String tableName) {
		return "INSERT INTO " + tableName + " ";
	}

	/**
	 * insert ignore into tableName - 动态表名
	 *
	 * @param entityClass      实体类
	 * @param defaultTableName 默认表名
	 */
	public static String insertIgnoreIntoTable(Class<?> entityClass, String defaultTableName) {
		return "INSERT IGNORE INTO " + getDynamicTableName(entityClass, defaultTableName) + " ";
	}

	/**
	 * insert ignore into tableName
	 *
	 * @param tableName 表名
	 */
	public static String insertBatchIgnoreIntoTable(String tableName) {
		return "INSERT IGNORE INTO " + tableName + " ";
	}

	/**
	 * insert指定列
	 *
	 * @param entityClass 实体类
	 */
	public static String insertColumns(Class<?> entityClass, FieldFilter fieldFilter, boolean skipPrimaryKey) {
		Set<EntityColumn> columnList = skipPrimaryKey ? EntityTableAssisor.getNonePrimaryColumns(entityClass)
				: EntityTableAssisor.getAllColumns(entityClass);
		if (null != fieldFilter) {
			columnList = filter(columnList, fieldFilter);
		}
		StringBuilder sql = new StringBuilder();
		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		for (EntityColumn column : columnList) {
			if (!column.isInsertable()) {
				continue;
			}
			//当该列在数据库中明确定义为not null，则加上!=null的判断
			if (isNotNull(column)) {
				sql.append(getIfNotNull(column, column.getColumn() + ","));
			} else {
				sql.append(column.getColumn()).append(",");
			}
		}
		sql.append("</trim>");
		return sql.toString();
	}

	public static String insertValues(Class<?> entityClass) {
		Set<EntityColumn> columnList = EntityTableAssisor.getAllColumns(entityClass);
		StringBuilder sql = new StringBuilder();

		sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
		for (EntityColumn column : columnList) {
			if (!column.isInsertable()) {
				continue;
			}
			//当该列在数据库中明确定义为not null，必须加上!=null的判断
			String content = getColumnHolderWithComma(column);
			if (isNotNull(column)) {
				sql.append(getIfNotNull(column, content));
			} else {
				sql.append(content);
			}
		}
		sql.append("</trim>");
		return sql.toString();
	}

	public static String insertBatchColumns(Class<?> entityClass) {
		Set<EntityColumn> columnList = EntityTableAssisor.getAllColumns(entityClass);
		StringBuilder sql = new StringBuilder();

		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		columnList.stream()
				.filter(column -> column.isInsertable() && !column.isIdentity())
				.forEach(column -> sql.append(column.getColumn()).append(","));
		sql.append("</trim>");
		return sql.toString();
	}

	public static String insertBatchValues(final Class<?> entityClass, final String uniqueIdExpression) {
		Set<EntityColumn> columnList = EntityTableAssisor.getAllColumns(entityClass);
		StringBuilder sql = new StringBuilder();

		sql.append(" VALUES ");
		sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
		columnList.stream()
				.filter(column -> column.isInsertable() && column.isUUID())
				.forEach(column -> sql.append(_getUniqueIdSeg(column, "record", uniqueIdExpression)));
		sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		columnList.stream()
				.filter(column -> column.isInsertable() && !column.isIdentity())
				.forEach(column -> sql.append(column.getColumnHolder("record")).append(","));
		sql.append("</trim>");
		sql.append("</foreach>");
		return sql.toString();
	}

	/**
	 * 回写唯一键的值
	 * <p>
	 * 返回格式：<if test='trade_no == null and _parameter.trade_no = uniqueIdExpression'/>
	 */
	private static String _getUniqueIdSeg(final EntityColumn column, final String entityName, final String uniqueIdExpression) {
		String template = "<if test='%s == null and %s = %s'/>";
		String property = entityName + "." + column.getProperty();
		return String.format(template, property, property, uniqueIdExpression);
	}
}
