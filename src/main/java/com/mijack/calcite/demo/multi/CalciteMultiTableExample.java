package com.mijack.calcite.demo.multi;

import com.google.common.collect.Lists;
import com.mijack.calcite.demo.school.*;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CalciteMultiTableExample {
    public static void main(String[] args) throws SQLException {

        SchoolDemo schoolDemo = new SchoolDemo();

        // 创建 Schema
        InMemoryCommonSchema schoolSchema = new InMemoryCommonSchema();
        schoolSchema.add(schoolDemo.getStudents(), Student.class);
        schoolSchema.add(schoolDemo.getTeachers(), Teacher.class);
        schoolSchema.add(schoolDemo.getCourses(), Course.class);
        schoolSchema.add(schoolDemo.getEnrollments(), Enrollment.class);
        // 设置 Calcite 配置
        Properties info = new Properties();
        info.setProperty("caseSensitive", "false");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        rootSchema.add("school", schoolSchema);

        showTables(rootSchema);
        // 打印所有的table

        // 查询学生的信息
        executeSQl(connection, "SELECT * FROM school.student where studentID = 'S01' ");
        // 查询学生上课的信息

        executeSQl(connection, "SELECT  course.*, teacher.*" +
                " FROM school.enrollment enrollment" +
                " left join school.course course on  enrollment.courseID =  course.courseID" +
                " left join school.teacher teacher on  course.teacherID =  teacher.teacherID" +
                "  where enrollment.studentID = 'S01' ");

//        executeSQl(connection, "SELECT * FROM school.student where studentID = 'S01' ");

    }

    public static void showTables(Schema schema) {

        doShowTables(schema, "");

    }

    private static void doShowTables(Schema schema, String preFix) {
        for (String tableName : schema.getTableNames()) {
            System.out.println(preFix + tableName); // 打印表名
        }
        // 遍历 schema 中的所有表
        Set<String> subSchemaNames = schema.getSubSchemaNames();
        if (subSchemaNames != null) {
            subSchemaNames.forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    doShowTables(schema.getSubSchema(s), preFix + s + ".");
                }
            });
        }

    }

    private static void executeSQl(Connection connection, String sql) throws SQLException {
        // 执行查询

        System.out.println("============================================================");
        System.out.println("sql:" + sql);
        System.out.println("------------------------------------------------------------");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<Object> objects = Lists.newArrayList();
                for (int i = 0; i < columnCount; i++) {
                    objects.add(resultSet.getString(i + 1));
                }
                System.out.println(objects.stream().map(Object::toString).collect(Collectors.joining(",")));
            }
        }
    }
}