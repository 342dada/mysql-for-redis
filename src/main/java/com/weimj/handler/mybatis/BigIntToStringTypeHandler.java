package com.weimj.handler.mybatis;
import java.sql.*;
import org.apache.ibatis.type.*;
/**
 * @Author:Weimj
 * @Date: 2023/5/21  2:25
 */


@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.BIGINT)
public class BigIntToStringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, Long.parseLong(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long result = rs.getLong(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return Long.toString(result);
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long result = rs.getLong(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return Long.toString(result);
        }
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long result = cs.getLong(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return Long.toString(result);
        }
    }
}
