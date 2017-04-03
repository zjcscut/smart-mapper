package org.throwable.mapper.support.handler.impl;

import org.throwable.mapper.support.handler.NameStyleHandler;
import org.throwable.mapper.utils.Strings;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/30 13:28
 */
public class CamelCaseToUnderLineNameStyleHandler implements NameStyleHandler {

	@Override
	public String contert(String input) {
		return Strings.camelhumpToUnderline(input);
	}
}
