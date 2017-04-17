package org.throwable.mapper.support.provider;


import java.util.List;
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

    interface SelectOneProcessor {

        void beforeExecuteSingle(Map<String, Object> paramsMap);

        <T> T executeSingleOperation(String msId, Map<String, Object> paramsMap);

        void afterSingleExecute();
    }

    protected <T> T selectOneOperation(String msId, Map<String, Object> paramsMap, SelectOneProcessor selectOneProcessor) {
        selectOneProcessor.beforeExecuteSingle(paramsMap);
        T t = selectOneProcessor.executeSingleOperation(msId, paramsMap);
        selectOneProcessor.afterSingleExecute();
        return t;
    }

    interface SelectListProcessor {

        void beforeExecute(Map<String, Object> paramsMap);

        <T> List<T> executeOperation(String msId, Map<String, Object> paramsMap);

        void afterExecute();
    }

    protected <T> List<T> selectListOperation(String msId, Map<String, Object> paramsMap, SelectListProcessor selectListProcessor) {
        selectListProcessor.beforeExecute(paramsMap);
        List<T> list = selectListProcessor.executeOperation(msId, paramsMap);
        selectListProcessor.afterExecute();
        return list;
    }

    interface CountProcessor{

        void beforeExecute(Map<String, Object> paramsMap);

        long  executeOperation(String msId, Map<String, Object> paramsMap);

        void afterExecute();
    }

    protected long countOperation(String msId, Map<String, Object> paramsMap,CountProcessor countProcessor){
          countProcessor.beforeExecute(paramsMap);
          long count = countProcessor.executeOperation(msId, paramsMap);
          countProcessor.afterExecute();
          return count;
    }

}
