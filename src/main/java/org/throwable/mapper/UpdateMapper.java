package org.throwable.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import org.throwable.mapper.support.provider.UpdateMapperProvider;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:58
 */
public interface UpdateMapper <T>{

    @UpdateProvider(type = UpdateMapperProvider.class,method = "dynamicSQL")
    int update(T t);

    @UpdateProvider(type = UpdateMapperProvider.class,method = "dynamicSQL")
    int batchUpdate(List<T> t);
}
