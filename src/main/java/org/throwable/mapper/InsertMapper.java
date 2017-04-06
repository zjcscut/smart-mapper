package org.throwable.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.throwable.mapper.support.filter.FieldFilter;
import org.throwable.mapper.support.provider.InsertMapperProvider;

import static org.throwable.mapper.common.constant.CommonConstants.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/3 23:57
 */
public interface InsertMapper<T> {

	default int insert(T t){
		return insert(t,null,true);
	}

	@InsertProvider(type = InsertMapperProvider.class,method = "dynamicSQL")
	int insert(@Param(PARAM_RECORD) T t,@Param(FIELD_FILTER) FieldFilter fieldFilter,@Param(SKIP_PRIMARYKEY) boolean skipPrimaryKey);

}
