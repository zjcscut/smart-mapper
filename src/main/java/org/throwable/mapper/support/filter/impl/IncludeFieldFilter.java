package org.throwable.mapper.support.filter.impl;

import org.throwable.mapper.common.constant.CommonConstants;
import org.throwable.mapper.support.filter.FieldFilter;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/31 0:14
 */
public class IncludeFieldFilter implements FieldFilter {

	@Override
	public boolean isInculdeFilter() {
		return true;
	}

	@Override
	public String[] accept(String fields) {
		return fields.split(CommonConstants.COMMA);
	}
}
