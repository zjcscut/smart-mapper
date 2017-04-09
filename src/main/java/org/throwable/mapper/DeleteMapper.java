package org.throwable.mapper;

import lombok.NonNull;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.provider.DeleteMapperProvider;


import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:59
 */
public interface DeleteMapper<T> {

	@DeleteProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
	int deleteByPrimaryKey(Object key);

	@DeleteProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
	int delete(@NonNull T t);

	@DeleteProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
	int deleteByCondition(@NonNull Condition condition);

	@DeleteProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
	int deleteByField(@NonNull @Param(PARAM_FIELD) String field, @NonNull @Param(PARAM_VALUE) Object value);
}
