package org.throwable.mapper.support.handler.impl;

import org.throwable.mapper.support.handler.NameStyleHandler;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/30 23:09
 */
public class LowerCaseNameStyleHandler implements NameStyleHandler {

	@Override
	public String contert(String input) {
		return input.toLowerCase();
	}
}
