package com.techyogi.spring.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String studentId;
    private String studentName;
    private String studentAddress;
    private String studentPhone;
    private String marks;

}
