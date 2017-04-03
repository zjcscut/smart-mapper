package org.throwable.mapper.support.filter.impl;

import org.throwable.mapper.common.constant.CommonConstants;
import org.throwable.mapper.support.filter.FieldFilter;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/31 0:17
 */
public class ExcludeFieldFilter implements FieldFilter {

	@Override
	public boolean isInculdeFilter() {
		return false;
	}

	@Override
	public String[] accept(String fields) {
		return fields.split(CommonConstants.COMMA);
	}
}
