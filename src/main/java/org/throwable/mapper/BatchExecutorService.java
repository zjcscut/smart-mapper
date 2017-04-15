package org.throwable.mapper;


import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.plugins.pagination.PageModel;
import org.throwable.mapper.support.plugins.pagination.Pager;

import java.util.List;

import static org.throwable.mapper.support.provider.BatchExecutor.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/10 2:29
 */

public interface BatchExecutorService {

    default <T> int executeBatchInsert(List<T> list) {
        return executeBatchInsert(list, DEFAULT_BATCH_SIZE);
    }

    <T> int executeBatchInsert(List<T> list, int batchSize);

    default <T> int executeBatchUpdate(List<T> list, boolean skipNull) {
        return executeBatchUpdate(list, DEFAULT_BATCH_SIZE, skipNull);
    }

    default <T> int executeBatchUpdate(List<T> list) {
        return executeBatchUpdate(list, DEFAULT_BATCH_SIZE, NONE_SKIP_NULL);
    }

    <T> int executeBatchUpdate(List<T> list, int batchSize, boolean skipNull);

    default <T> int update(T t) {
        return update(t, NONE_SKIP_NULL);
    }

    <T> int update(T t, boolean skipNull);

    default <T> int updateByCondition(T t, Condition condition) {
        return updateByCondition(t, condition, NONE_SKIP_NULL);
    }

    <T> int updateByCondition(T t, Condition condition, boolean skipNull);

    <T> T selectOneByCondition(Class<T> clazz, Condition condition);

    <T> List<T> selectListByCondtion(Class<T> clazz, Condition condition);

    <T> long countByCondtion(Class<T> clazz, Condition condition);

    <T> PageModel<T> selectListByCondtionPage(Class<T> clazz, Condition condition, Pager pager);
}
