package org.throwable.mapper;


import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/10 2:29
 */

public interface BatchExecutorService {

	int DEFAULT_BATCH_SIZE = 100;

	default <T> int executeBatchInsert(List<T> list){
		return executeBatchInsert(list,DEFAULT_BATCH_SIZE);
	}

	<T> int executeBatchInsert(List<T> list,int batchSize);

	default <T> int executeBatchUpdate(List<T> list){
		return executeBatchUpdate(list,DEFAULT_BATCH_SIZE);
	}

	<T> int executeBatchUpdate(List<T> list,int batchSize);
}
