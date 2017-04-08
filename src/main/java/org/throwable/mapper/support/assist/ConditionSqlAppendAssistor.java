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


	public static String getSelectColumnsClause(Class<?> entityClass){
		return EntityTableAssisor.getAllColumns(entityClass).stream()
				.map(EntityColumn::getColumn)
				.reduce((c1,c2) -> c1 +"," + c2)
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
				"    <foreach collection=\"" + conditionEntity + ".criterias\" item=\"criterion\" separator=\"or\">\n" +
				"     <if test=\"criterion.valid\">\n" +
				"        <trim prefixOverrides=\"and\" >\n" +
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
				"        </trim>\n" +
				"      </if>\n" +
				"    </foreach>\n" +
				"  </if>\n" +
				"</where>";
	}

	/**
	 * Condition查询中的order结构
	 */
	public static String conditionOrderByClause() {
		return "<trim prefix=\"ORDER BY\">\n" +
				"  <foreach collection=\"orders\" item=\"order\" separator=\", \">\n" +
				"    ${property} ${direction}" +
				"  </foreach>\n" +
				"</trim>";
	}

	/**
	 * Condition查询中的limit结构
	 */
	public static String conditionLimitByClause() {
		return getIfNotNull("limit", "limit #{limit.offset}, #{limit.size}");
	}
}
