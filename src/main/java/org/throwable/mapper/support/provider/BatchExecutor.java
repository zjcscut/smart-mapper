package org.throwable.mapper.support.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/10 10:57
 */
public abstract class BatchExecutor {

    protected <T>int batchUpdate(List<T> list, Class<?> clazz,int batchSize){
        int commitCount = (int) Math.ceil(list.size() / (double) batchSize);
        List<T> tempList = new ArrayList<>(batchSize);
        int start, stop;
        for (int i = 0; i < commitCount; i++) {
            tempList.clear();
            start = i * batchSize;
            stop = Math.min(i * batchSize + batchSize - 1, list.size() - 1);
            for (int j = start; j <= stop; j++) {
                tempList.add(list.get(j));
            }
            session.insert(mybatisSQLId, tempList);
            session.commit();
            session.clearCache();
        }
    }

    protected <T>int batchInsert(List<T> list, Class<?> clazz,int batchSize){

    }


}
