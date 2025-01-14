package com.example.lms.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseData {

    private String title;

    private String description;

    private Integer duration;

    private Integer instructorId;

}
