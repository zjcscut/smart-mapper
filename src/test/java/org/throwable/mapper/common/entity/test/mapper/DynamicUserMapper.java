package org.throwable.mapper.common.entity.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.throwable.mapper.common.entity.test.UserLong;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/16 4:20
 */
@Mapper
public interface DynamicUserMapper {

	int insertUser(UserLong user);
}
