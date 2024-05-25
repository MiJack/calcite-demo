package com.mijack.calcite.demo.multi;

import com.google.common.collect.Lists;
import com.mijack.calcite.demo.school.Student;
import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class InMemoryCommonTable<T> extends AbstractTable implements ScannableTable {
    private final List<T> data;
    private final Class<T> clazz;


    public InMemoryCommonTable(List<T> data, Class<T> clazz) {
        this.data = data;
        this.clazz = clazz;
    }

    @Override
    public Enumerable<Object[]> scan(DataContext root) {
        return Linq4j.asEnumerable(data)
                .select(s ->
                {
                    List<Object> objects = Lists.newArrayList();
                    for (Field declaredField : clazz.getDeclaredFields()) {
                        declaredField.setAccessible(true);
                        try {
                            objects.add(declaredField.get(s));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    return objects.toArray();

                });
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        RelDataTypeFactory.FieldInfoBuilder builder = typeFactory.builder();
        for (Field declaredField : clazz.getDeclaredFields()) {
            builder.add(declaredField.getName(), toSqlType(declaredField.getType()));
        }

        return builder.build();
    }

    private SqlTypeName toSqlType(Class<?> type) {
        if (type.equals(Long.class)) {
            return SqlTypeName.BIGINT;
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return SqlTypeName.INTEGER;
        } else if (type.equals(String.class)) {
            return SqlTypeName.VARCHAR;
        }

        // todo 按需进行优化
        return null;
    }
}
