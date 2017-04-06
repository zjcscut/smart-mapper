package org.throwable.mapper;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:59
 */
public interface SelectMapper <T>{

	List<T> selectCondition();

	long countCondition();
}
