package org.throwable.mapper.support.plugins.condition;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.common.entity.EntityTable;
import org.throwable.mapper.exception.UnsupportedElementException;
import org.throwable.mapper.support.assist.EntityTableAssisor;
import org.throwable.mapper.support.assist.FieldFilterAssistor;
import org.throwable.mapper.support.filter.FieldFilter;
import org.throwable.mapper.support.plugins.sort.Direction;
import org.throwable.mapper.support.plugins.sort.Order;
import org.throwable.mapper.support.plugins.sort.Sort;
import org.throwable.mapper.utils.ArraysUtils;

import javax.validation.constraints.Min;
import java.util.*;
import java.util.stream.Collectors;

import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description 查询条件拼接
 * @since 2017/4/7 3:27
 */
public class Condition {

	@Getter
	private final Class<?> entity;

	@Getter
	private final Sort sort;

	@Getter
	private FieldFilter fieldFilter;

	@Getter
	private boolean isDistinct;

	@Getter
	@Setter
	private boolean forceMode = false;

	@Getter
	private final EntityTable entityTable;

	private final Map<String, EntityColumn> propertyMap;

	@Getter
	private Set<String> selectColumns;

	@Getter
	private LinkedList<CriteriaCollection> criteriaCollection;

	@Getter
	private Limit limit;

	private Condition(Class<?> entity) {
		this.entity = entity;
		this.sort = new Sort();
		this.criteriaCollection = Lists.newLinkedList();
		this.criteriaCollection.addLast(new CriteriaCollection());
		this.entityTable = EntityTableAssisor.getEntityTable(entity);
		this.propertyMap = entityTable.getPropertyMap();
	}

	public static Condition create(Class<?> entity) {
		return new Condition(entity);
	}

	public Condition orderBy(String property, String clause) {
		if (checkMatchColumn(property))
			sort.addSort(new Order(Direction.fromString(clause), matchColumn(property)));
		return this;
	}

	public Condition desc(String property) {
		if (checkMatchColumn(property))
			sort.addSort(new Order(Direction.DESC, matchColumn(property)));
		return this;
	}

	public Condition asc(String property) {
		if (checkMatchColumn(property))
			sort.addSort(new Order(Direction.ASC, matchColumn(property)));
		return this;
	}

	public Condition addFieldFilter(FieldFilter fieldFilter) {
		boolean check = false;
		for (String temp : fieldFilter.accept()) {
			check = checkMatchColumn(temp);
		}
		if (check) {
			this.fieldFilter = fieldFilter;
			this.selectColumns = convertFieldFilterToSelectColumns(fieldFilter, entityTable.getEntityClass());
		}
		return this;
	}

	public Condition distinct(boolean isDistinct) {
		this.isDistinct = isDistinct;
		return this;
	}

	public Condition eq(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_EQ, value));
		return this;
	}

	public Condition gt(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_GT, value));
		return this;
	}

	public Condition gteq(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_GTEQ, value));
		return this;
	}

	public Condition lt(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_LT, value));
		return this;
	}

	public Condition lteq(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_LTEQ, value));
		return this;
	}

	public Condition and(String key, @NonNull String op, Object value) {
		switch (op.trim().toUpperCase(Locale.US)) {
			case "=":
				eq(key, value);
				break;
			case ">":
				gt(key, value);
				break;
			case "<":
				lt(key, value);
				break;
			case ">=":
				gteq(key, value);
				break;
			case "<=":
				lteq(key, value);
				break;
			case "IN":
				in(key, value);
				break;
			case "NOT IN":
				notIn(key, value);
				break;
			case "LIKE":
				like(key, value);
				break;
			case "NOT LIKE":
				notLike(key, value);
				break;
			default: {
				eq(key, value);
			}
		}
		return this;
	}

	public Condition in(String key, Object values) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_IN, convertStringToCollection(values)));
		return this;
	}

	public Condition in(String key, Collection<?> values) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_IN, values));
		return this;
	}

	public Condition notIn(String key, Object values) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_NOT_IN, convertStringToCollection(values)));
		return this;
	}

	public Condition notIn(String key, Collection<?> values) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_NOT_IN, values));
		return this;
	}

	public Condition notLike(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_NOT_LIKE, value));
		return this;
	}

	public Condition like(String key, Object value) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_LIKE, value));
		return this;
	}

	public Condition between(String key, Object leftValue, Object rightValue) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_BETWEEN, leftValue, rightValue));
		return this;
	}

	public Condition isTrue(String key) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_IS_TRUE));
		return this;
	}

	public Condition isNull(String key) {
		if (checkMatchColumn(key))
			this.criteriaCollection.getLast().addCriteria(new Criteria(matchColumn(key) + CONDTION_CLAUSE_IS_NULL));
		return this;
	}

	/**
	 * or子句特殊处理,新建一个条件集合作为or子句
	 */
	public Condition or(String key, String op, Object value) {
		if (checkMatchColumn(key)) {
			this.criteriaCollection.addLast(new CriteriaCollection());
			and(key, op, value);
		}
		return this;
	}

	public Condition limit(int offset, int size) {
		this.limit = new Limit(offset, size);
		return this;
	}

	/**
	 * 条件集合
	 */
	@Getter
	public static class CriteriaCollection {

		private LinkedList<Criteria> criterias;

		public CriteriaCollection() {
			criterias = Lists.newLinkedList();
		}

		public void addCriteria(Criteria criteria) {
			criterias.addLast(criteria);
		}

		//是否合法,至少包含一个条件语句
		public boolean valid() {
			return criterias.size() > 0;
		}
	}


	/**
	 * 单个条件元素
	 */
	@Getter
	public static class Criteria {

		private String conditionClause;  //条件子句
		private Object leftValue;  //左值
		private Object rightValue; //右值
		private boolean noneValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean collectionValue;

		public Criteria(String conditionClause) {
			super();
			this.conditionClause = conditionClause;
			this.noneValue = true;
		}

		public Criteria(String conditionClause, Object value) {
			super();
			this.conditionClause = conditionClause;
			this.leftValue = value;
			if (value instanceof Collection<?>) {
				this.collectionValue = true;
			} else {
				this.singleValue = true;
			}
		}

		public Criteria(String conditionClause, Object leftValue, Object rightValue) {
			super();
			this.conditionClause = conditionClause;
			this.leftValue = leftValue;
			this.rightValue = rightValue;
			this.betweenValue = true;
		}

	}


	@Getter
	private static class Limit {

		@Min(0)
		private final int offset;

		@Min(1)
		private final int size;

		public Limit(int offset, int size) {
			if (offset < 0) {
				throw new IllegalArgumentException("Limit field offset must not be lt 0");
			}
			if (size < 1) {
				throw new IllegalArgumentException("Limit field size must not be lt 1");
			}
			this.offset = offset;
			this.size = size;
		}
	}


	private boolean checkMatchColumn(String key) {
		boolean match = propertyMap.containsKey(key);
		if (this.forceMode && !match) {
			throw new UnsupportedElementException(String.format("实体类%s不包含属性:%s",
					entityTable.getEntityClass().getCanonicalName(), key));
		}
		return match;
	}

	private String matchColumn(String key) {
		return propertyMap.get(key).getColumn();
	}

	private Collection<?> convertStringToCollection(Object value) {
		if (value instanceof String) {
			String[] values = ((String) value).split(COMMA);
			return ArraysUtils.arrayToList(values);
		}
		return (Collection<?>) value;
	}

	private Set<String> convertFieldFilterToSelectColumns(FieldFilter fieldFilter, Class<?> entityClass) {
		if (null != fieldFilter) {
			return FieldFilterAssistor.getFilterColumns(entityClass, fieldFilter, false).stream()
					.map(EntityColumn::getColumn)
					.collect(Collectors.toSet());
		}
		return null;
	}

}
