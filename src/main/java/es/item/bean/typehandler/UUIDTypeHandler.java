package es.item.bean.typehandler;

import es.item.util.HexUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by gujinxin on 16/8/12.
 */
public class UUIDTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, HexUtil.unhexUUID(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        byte[] idBytes = resultSet.getBytes(s);
        UUID uuid = HexUtil.toUUID(idBytes);
        return uuid.toString();
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        byte[] idBytes = resultSet.getBytes(i);
        UUID uuid = HexUtil.toUUID(idBytes);
        return uuid.toString();
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        if (callableStatement.wasNull()) {
            return null;
        }
        byte[] idBytes = callableStatement.getBytes(i);
        UUID uuid = HexUtil.toUUID(idBytes);
        return uuid.toString();
    }
}
