package org.throwable.mapper.common.entity.test;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/2 15:06
 */
public class TypeHandlerTest extends BaseTypeHandler<User> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, User parameter, JdbcType jdbcType) throws SQLException {

	}

	@Override
	public User getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public User getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public User getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
}
