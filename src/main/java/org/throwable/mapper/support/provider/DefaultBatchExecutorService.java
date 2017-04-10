package org.throwable.mapper.support.provider;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.throwable.mapper.BatchExecutorService;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/10 2:30
 */
@Service(value = "batchExecutorService")
public class DefaultBatchExecutorService extends BatchExecutor implements BatchExecutorService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public <T> int executeBatchInsert(List<T> list, int batchSize) {
        Assert.notEmpty(list, "executeBatchInsert list must not be empey!");
        Class<?> clazz = list.get(0).getClass();
        return 0;
    }


    @Override
    public <T> int executeBatchUpdate(List<T> list, int batchSize) {
        Assert.notEmpty(list, "executeBatchUpdate list must not be empey!");
        Class<?> clazz = list.get(0).getClass();
        return 0;
    }
}
