package org.throwable.mapper.support.provider;

import com.google.common.collect.Maps;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.Assert;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.configuration.prop.PropertiesConfiguration;
import org.throwable.mapper.support.assist.EntityTableAssisor;

import static org.throwable.mapper.common.constant.CommonConstants.*;
import static org.throwable.mapper.support.assist.UpdateSqlAppendAssistor.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/10 10:57
 */
public abstract class BatchExecutor implements Executors {

	public static final int DEFAULT_BATCH_SIZE = 20;

	protected void assertListParams(List list, int batchSize) {
		assertNotEmpty(list, "executeBatch list must not be empey!");
		assertBatchSize(batchSize, "batchSize must be greater than 0!");
	}

	protected void assertNotEmpty(List list, String message) {
		Assert.notEmpty(list, message);
	}

	protected void assertBatchSize(int batchSize, String message) {
		if (batchSize <= 0) {
			throw new IllegalArgumentException(message);
		}
	}

	interface BatchProcessor {

		<T> void beforeExecuteBatch(List<T> list);

		void executeBatchOperation(String msId, Map<String, Object> paramsMap);

		void afterExecuteBatch();
	}

	protected Map<String, Object> buildParamsMap(String tableName) {
		Map<String, Object> params = Maps.newHashMap();
		params.put(DYNAMICT_TABLENAME, tableName);
		return params;
	}

	protected void checkExistsPriamryKey(Class<?> clazz) {
		Assert.notNull(EntityTableAssisor.getPrimaryColumn(clazz), "Primary key column must not be null");
	}

	protected EntityColumn checkExistsPriamryKeyAndReturn(Class<?> clazz) {
		EntityColumn primaryKeyColumn = EntityTableAssisor.getPrimaryColumn(clazz);
		Assert.notNull(primaryKeyColumn, "Primary key column must not be null");
		return primaryKeyColumn;
	}


	protected <T> int batchOperation(List<T> list, int batchSize, String msId, Map<String, Object> paramsMap, BatchProcessor batchProcessor) {
		batchProcessor.beforeExecuteBatch(list);
		int commitCount = (int) Math.ceil(list.size() / (double) batchSize);
		List<T> tempList = new ArrayList<>(batchSize);
		int start, stop;
		for (int batchIndex = 0; batchIndex < commitCount; batchIndex++) {
			tempList.clear();
			start = batchIndex * batchSize;
			stop = Math.min(batchIndex * batchSize + batchSize - 1, list.size() - 1);
			for (int executeIndex = start; executeIndex <= stop; executeIndex++) {
				tempList.add(list.get(executeIndex));
				paramsMap.put(PARAM_RECORDS, tempList);
			}
			batchProcessor.executeBatchOperation(msId, paramsMap);
		}
		batchProcessor.afterExecuteBatch();
		return commitCount;
	}

	protected String createDynamicBatchUpdateScriptSql(Class<?> clazz) {
		return updateDynamicTable() +
				batchUpdateSetColumns(clazz);
	}

	protected <T> void autoCreatePrimaryKeyByOgnlStrategy(List<T> list, EntityColumn keyColumn,String strategy) {
		list.forEach(l -> {
			MetaObject target = SystemMetaObject.forObject(l);
			try {
				target.setValue(keyColumn.getProperty(), Ognl.getValue(strategy,null));
			} catch (OgnlException e) {
				throw new UnsupportedOperationException(e);
			}
		});
	}

	protected String createDynamicBatchInsertScriptSql(Class<?> clazz) {
		return updateDynamicTable() +
				batchUpdateSetColumns(clazz);
	}


}
