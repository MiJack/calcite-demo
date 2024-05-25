package com.mijack.calcite.demo.single;

import com.mijack.calcite.demo.school.Student;
import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;

import java.util.List;


public class InMemoryStudentTable extends AbstractTable implements ScannableTable {
    private final List<Student> students;

    public InMemoryStudentTable(List<Student> students) {
        this.students = students;
    }

    @Override
    public Enumerable<Object[]> scan(DataContext root) {
        return Linq4j.asEnumerable(students)
                .select(s -> new Object[]{s.getStudentID(), s.getName(), s.getAge(), s.getMajor()});
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return typeFactory.builder()
                .add("ID", SqlTypeName.VARCHAR)
                .add("Name", SqlTypeName.VARCHAR)
                .add("Age", SqlTypeName.INTEGER)
                .add("Major", SqlTypeName.VARCHAR)
                .build();
    }
}
