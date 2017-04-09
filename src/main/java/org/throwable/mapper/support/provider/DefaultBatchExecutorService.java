package org.throwable.mapper.support.provider;

import org.springframework.stereotype.Service;
import org.throwable.mapper.BatchExecutorService;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/10 2:30
 */
@Service(value = "batchExecutorService")
public class DefaultBatchExecutorService implements BatchExecutorService {


	@Override
	public <T> int executeBatchInsert(List<T> list, int batchSize) {
		return 0;
	}


	@Override
	public <T> int executeBatchUpdate(List<T> list, int batchSize) {
		return 0;
	}
}
