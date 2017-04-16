package org.throwable.mapper.support.provider;


import java.util.Map;

import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/16 23:46
 */
public abstract class SingleExecutor {

	interface SingleProcessor {

		<T> void beforeExecuteSingle(T t, Map<String, Object> paramsMap);

		int executeSingleOperation(String msId, Map<String, Object> paramsMap);

		void afterSingleExecute();
	}

	protected <T> int singleOperation(T t, String msId, Map<String, Object> paramsMap, SingleProcessor processor) {
		processor.beforeExecuteSingle(t, paramsMap);
		int exexuteCount = processor.executeSingleOperation(msId, paramsMap);
		processor.afterSingleExecute();
		return exexuteCount;
	}

}
