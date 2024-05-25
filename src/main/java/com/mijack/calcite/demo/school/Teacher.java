package com.mijack.calcite.demo.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 老师类
public class Teacher {
    String teacherID;
    String name;
    String department;

}

