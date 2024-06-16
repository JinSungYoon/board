package com.project.board.handler;

import com.project.board.enumeration.UserRoleCode;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTypeHandler implements TypeHandler<UserRoleCode> {

    @Override
    public void setParameter(PreparedStatement ps, int i, UserRoleCode parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.getCode());
    }

    @Override
    public UserRoleCode getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnum(code);
    }

    @Override
    public UserRoleCode getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnum(code);
    }

    @Override
    public UserRoleCode getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnum(code);
    }

    private UserRoleCode getCodeEnum(String code){
        return switch (code) {
            case "MEMBER" -> UserRoleCode.MEB;
            case "GUEST" -> UserRoleCode.GST;
            default -> null;
        };
    }
}
