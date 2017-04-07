package org.throwable.mapper.support.plugins.condition;

import lombok.Getter;
import org.throwable.mapper.support.plugins.sort.Direction;
import org.throwable.mapper.support.plugins.sort.Order;
import org.throwable.mapper.support.plugins.sort.Sort;

/**
 * @author throwable
 * @version v1.0
 * @description 查询条件拼接
 * @since 2017/4/7 3:27
 */
public class Condition {

	@Getter
	public final Sort sort;

	private Condition() {
		sort = new Sort();
	}

	public static Condition create() {
		return new Condition();
	}

	public Condition orderBy(String property, String clause) {
		sort.addSort(new Order(Direction.fromString(clause), property));
		return this;
	}

	public Condition desc(String property) {
		sort.addSort(new Order(Direction.DESC, property));
		return this;
	}

	public Condition asc(String property) {
		sort.addSort(new Order(Direction.ASC, property));
		return this;
	}
}
