package com.aca.certification.ws.dto;

import com.aca.certification.infrastructure.entity.Applicant;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class CourseDTO {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String teacherName;
    @NotNull
    private String description;
    @NotNull
    private Set<ApplicantDTO> applicantSet;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private LocalDate startDate;
}
