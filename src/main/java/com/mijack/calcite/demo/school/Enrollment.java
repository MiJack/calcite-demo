package com.mijack.calcite.demo.school;

// 选课关系类

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {
    String studentID;
    String courseID;

}
