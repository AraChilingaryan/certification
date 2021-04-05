package com.aca.certification.core.model;

import com.aca.certification.infrastructure.entity.Applicant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {
    private Integer id;
    private String name;
    private String teacherName;
    private String description;
    private LocalDate endDate;
    private LocalDate startDate;
    private Set<ApplicantModel> applicantSet;

}
