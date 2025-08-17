package com.yuzarsif.productcatalogueservice.config;

import com.yuzarsif.productcatalogueservice.model.BrandStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(BrandStatus.class)
public class BrandStatusTypeHandler extends BaseTypeHandler<BrandStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BrandStatus parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("brand_status_enum");
        pgObject.setValue(parameter.name());
        ps.setObject(i, pgObject);
    }

    @Override
    public BrandStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : BrandStatus.valueOf(value);
    }

    @Override
    public BrandStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : BrandStatus.valueOf(value);
    }

    @Override
    public BrandStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : BrandStatus.valueOf(value);
    }
}
