package org.throwable.mapper.support.plugins.sort;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/7 12:33
 */
@Data
public class Order {

	public static final Direction DEFAULT_DIRECTION = Direction.ASC;
	private final Direction direction;
	private final String property;

	public Order(Direction direction, String property) {
		if (StringUtils.isBlank(property)){
			throw new IllegalArgumentException("Order properties must not be blank");
		}
		this.direction = direction;
		this.property = property;
	}

	public Order(String property) {
		this.property = property;
		this.direction = DEFAULT_DIRECTION;
	}

	public String getOrderClause(){
		return this.getProperty() + " " + this.getDirection();
	}
}
