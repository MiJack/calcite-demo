package com.mijack.calcite.demo.single;

import com.mijack.calcite.demo.school.SchoolDemo;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.*;
import java.util.Properties;

public class CalciteSingleTableExample {
    public static void main(String[] args) throws SQLException {


        // 创建 Schema
        Schema schoolSchema = new InMemoryStudentSchema(new SchoolDemo().getStudents());

        // 设置 Calcite 配置
        Properties info = new Properties();
        info.setProperty("caseSensitive", "false");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        rootSchema.add("school", schoolSchema);

        executeSQl(connection, "SELECT * FROM school.students");
        executeSQl(connection, "SELECT * FROM school.students where ID = 'S01' ");

    }

    private static void executeSQl(Connection connection, String sql) throws SQLException {
        // 执行查询

        System.out.println("============================================================");
        System.out.println("sql:" + sql);
        System.out.println("------------------------------------------------------------");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String course = resultSet.getString("major");
                System.out.println(id + ", " + name + ", " + age + ", " + course);
            }
        }
    }
}