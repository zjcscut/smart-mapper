package org.throwable.mapper.support.provider;

/**
 * @author throwable
 * @version v1.0
 * @description 动态批量sql执行标识接口
 * @since 2017/4/13 22:58
 */
public interface Executors {

	boolean AUTO_COMMIT = false;

	int DEFAULT_BATCH_SIZE = 20;

	String DYNAMIC_BATCHUPDATE = "dynamicBatchUpdate";

	String DYNAMIC_BATCHINSERT = "dynamicBatchInsert";
}
