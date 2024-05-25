package com.mijack.calcite.demo.multi;


import com.google.common.collect.Maps;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.List;
import java.util.Map;

public class InMemoryCommonSchema extends AbstractSchema {

    Map<String, Table> tableMap = Maps.newHashMap();


    public <T> void add(List<T> data, Class<T> clazz) {
        tableMap.put(toLowerCaseFirstChar(clazz.getSimpleName()), new InMemoryCommonTable<>(data, clazz));

    }

    public static String toLowerCaseFirstChar(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        if (input.length() == 1) {
            return input.toLowerCase();
        }
        // 将第一个字符转换为小写，然后与字符串的其余部分拼接
        return Character.toLowerCase(input.charAt(0)) + input.substring(1);
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return tableMap;
    }

}
