package com.mijack.calcite.demo.school;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Stream;

@Getter
public class SchoolDemo {
    final List<Teacher> teachers;
    final List<Course> courses;
    final List<Enrollment> enrollments;
    final List<Student> students;

    {

        // 创建老师列表
        teachers = Stream.of(
                new Teacher("T01", "Alice", "Math"),
                new Teacher("T02", "Bob", "Science"),
                new Teacher("T03", "Carol", "English"),
                new Teacher("T04", "David", "History"),
                new Teacher("T05", "Emily", "Art")
        ).toList();

        // 创建学生列表，至少10个学生
        students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(new Student("S0" + i, "Student" + i, 20 - i, "Major" + i % 5));
        }

        // 创建课程列表，至少5门课程
        courses = Stream.of(
                new Course("C01", "Calculus", "T01"),
                new Course("C02", "Physics", "T02"),
                new Course("C03", "Literature", "T03"),
                new Course("C04", "World History", "T04"),
                new Course("C05", "Painting", "T05")
        ).toList();

        // 创建选课关系列表，每个学生至少3门课
        enrollments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String studentID = "S0" + i;
            for (int j = 0; j < 3; j++) {
                String courseID = "C0" + (i + j);
                enrollments.add(new Enrollment(studentID, courseID));
            }
        }
    }

    public static void main(String[] args) {
        SchoolDemo schoolDemo = new SchoolDemo();

        // 打印输出，演示数据
        System.out.println("Teachers: " + schoolDemo.getTeachers());
        System.out.println("Students: " + schoolDemo.getStudents());
        System.out.println("Courses: " + schoolDemo.getCourses());
        System.out.println("Enrollments: " + schoolDemo.getEnrollments());
    }

}
