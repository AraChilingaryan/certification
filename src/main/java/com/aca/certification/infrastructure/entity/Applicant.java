package com.aca.certification.infrastructure.entity;

import com.aca.certification.utils.enumaration.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Applicant {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @Email(message = "email not valid")
    private String email;

    @NotNull
    @NotBlank(message="Please enter your phone number")
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String address;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
