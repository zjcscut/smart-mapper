package org.throwable.mapper.common.entity.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.throwable.mapper.SmartMapper;
import org.throwable.mapper.common.entity.test.User;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 2:15
 */
@Mapper
public interface UserMapper extends SmartMapper<User> {
}
