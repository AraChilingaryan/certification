package com.aca.certification.ws.dto;

import com.aca.certification.utils.enumaration.Status;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicantDTO {
    private int id;
    @NotNull
    private int course_id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Status status;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;

}
