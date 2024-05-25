package com.mijack.calcite.demo.single;


import com.google.common.collect.ImmutableMap;
import com.mijack.calcite.demo.school.Student;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.List;
import java.util.Map;

public class InMemoryStudentSchema extends AbstractSchema {
    private final List<Student> students;

    public InMemoryStudentSchema(List<Student> students) {
        this.students = students;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return ImmutableMap.of("students",  new InMemoryStudentTable(students));
    }

}
