package org.throwable.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.provider.SelectMapperProvider;

import java.util.List;

import static org.throwable.mapper.common.constant.CommonConstants.PARAM_CONDITION;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:59
 */
public interface SelectMapper<T> {

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    List<T> selectCondition(@Param(PARAM_CONDITION) Condition condition);

    @SelectProvider(type = SelectMapperProvider.class, method = "dynamicSQL")
    long countCondition(@Param(PARAM_CONDITION) Condition condition);

}
