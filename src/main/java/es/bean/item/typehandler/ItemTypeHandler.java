package es.bean.item.typehandler;

import es.bean.item.ItemType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cao on 3/24/16.
 */

/**
 * Created by gujinxin on 16/8/11.
 */
public class ItemTypeHandler extends BaseTypeHandler<ItemType> {
    private Class<ItemType> type;

    private final ItemType[] enums;

    public ItemTypeHandler(Class<ItemType> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ItemType itemType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, itemType.getId());
    }

    public ItemType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int i = resultSet.getInt(s);

        if (resultSet.wasNull()) {
            return null;
        }

        return ItemType.parseById(i);
    }

    public ItemType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int j = resultSet.getInt(i);
        if (resultSet.wasNull()) {
            return null;
        }
        return ItemType.parseById(j);
    }

    public ItemType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int j = callableStatement.getInt(i);
        if (callableStatement.wasNull()) {
            return null;
        }
        return ItemType.parseById(j);
    }
}
