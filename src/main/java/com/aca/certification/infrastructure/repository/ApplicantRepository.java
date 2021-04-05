package com.aca.certification.infrastructure.repository;

import com.aca.certification.infrastructure.entity.Applicant;
import com.aca.certification.infrastructure.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer > {
    List<Applicant> findAllByName (String name);
    Optional<Applicant> findAllByEmail (String email);
    List<Applicant> findAllByCourse (Course course);
}
