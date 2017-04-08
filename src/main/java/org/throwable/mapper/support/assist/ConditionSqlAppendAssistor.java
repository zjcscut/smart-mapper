package org.throwable.mapper.support.assist;

import org.throwable.mapper.common.entity.EntityColumn;

import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/8 13:20
 */
public abstract class ConditionSqlAppendAssistor extends SqlAppendAssistor {


	public static String getSelectColumnsClause(Class<?> entityClass) {
		return EntityTableAssisor.getAllColumns(entityClass).stream()
				.map(EntityColumn::getColumn)
				.reduce((c1, c2) -> c1 + "," + c2)
				.orElse("");
	}

	/**
	 * Condition查询中的where结构
	 */
	public static String conditionWhereClause() {
		return conditionWhereClause(PARAM_DEFAULT);
	}

	/**
	 * Condition查询中的where结构
	 */
	public static String conditionWhereClause(String parameterName) {
		String conditionEntity = getEntityPrefix(parameterName);
		return "<where>\n" +
				"  <if test=\"" + conditionEntity + " neq null\">\n" +
				"    <foreach collection=\"" + conditionEntity + ".criteriaCollection\" item=\"criteriaCollectionItem\" separator=\"or\">\n" +
				"     <if test=\"criteriaCollectionItem.valid\">\n" +
				"        <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n" +
				"          <foreach collection=\"criteriaCollectionItem.criterias\" item=\"criterion\">\n" +
				"            <choose>\n" +
				"              <when test=\"criterion.noneValue\">\n" +
				"                and ${criterion.conditionClause}\n" +
				"              </when>\n" +
				"              <when test=\"criterion.singleValue\">\n" +
				"                and ${criterion.conditionClause} #{criterion.leftValue}\n" +
				"              </when>\n" +
				"              <when test=\"criterion.betweenValue\">\n" +
				"                and ${criterion.conditionClause} #{criterion.leftValue} and #{criterion.rightValue}\n" +
				"              </when>\n" +
				"              <when test=\"criterion.collectionValue\">\n" +
				"                and ${criterion.conditionClause}\n" +
				"                <foreach close=\")\" collection=\"criterion.leftValue\" item=\"collectionItem\" open=\"(\" separator=\",\">\n" +
				"                  #{collectionItem}\n" +
				"                </foreach>\n" +
				"              </when>\n" +
				"            </choose>\n" +
				"          </foreach>\n" +
				"        </trim>\n" +
				"      </if>\n" +
				"    </foreach>\n" +
				"  </if>\n" +
				"</where>\n";
	}

	/**
	 * Condition查询中的order结构
	 */
	public static String conditionOrderByClause(String parameterName) {
		String conditionEntity = getEntityPrefix(parameterName);

		return "<if test=\"" + conditionEntity + " neq null and @org.throwable.mapper.utils.OGNL@hasOrderByClause(condition)\"> \n" +
				"<trim prefix=\"ORDER BY\">\n" +
				"  <foreach collection=\"" + conditionEntity + ".sort.orders\" item=\"order\" separator=\", \">\n" +
				"    ${order.property} ${order.direction}" +
				"  \n</foreach>\n" +
				"</trim>\n" +
				"</if>\n";
	}


	public static String conditionLimitClause(String parameterName) {
		String pagerEntity = getEntityPrefix(parameterName);
		return getIfNotNull(pagerEntity, String.format(" LIMIT #{%s.offset}, #{%s.size}", pagerEntity, pagerEntity));
	}
}
