package com.aca.certification.core.model;

import com.aca.certification.utils.enumaration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantModel {
    private Integer id;
    private int course_id;
    private String name;
    private String email;
    private Status status;
    private String phoneNumber;
    private String address;

}
