package com.mijack.calcite.demo.school;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 课程类
public class Course {
    String courseID;
    String name;
    String teacherID;
}
