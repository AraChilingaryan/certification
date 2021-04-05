package com.aca.certification.infrastructure.entity;


import com.aca.certification.utils.enumaration.Status;
import com.fasterxml.jackson.annotation.*;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull
    private String teacherName;

    @NotNull
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "course",
            cascade = { CascadeType.ALL},fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Applicant> applicantSet;

    public Course(String name, String teacherName, String description) {
        this.name = name;
        this.teacherName = teacherName;
        this.description = description;
    }

}
