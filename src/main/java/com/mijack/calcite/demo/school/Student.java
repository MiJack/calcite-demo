package com.mijack.calcite.demo.school;

// 学生类

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public
class Student {
    String studentID;
    String name;
    Integer age;
    String major;

}
