package com.aca.certification.core.service;

import com.aca.certification.core.model.ApplicantModel;
import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.exception.ApplicantNotFoundException;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.infrastructure.entity.Applicant;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface ApplicantService {
    ApplicantModel getApplicantById(int id) throws ApplicantNotFoundException;
    List<ApplicantModel> getAllApplicants();
    ApplicantModel saveApplicant(ApplicantModel applicantModel) throws ValidationException, CourseNotFoundException;
    ApplicantModel updateApplicant(int id,ApplicantModel applicantModel)throws ValidationException, CourseNotFoundException, ApplicantNotFoundException;
    void deleteApplicantById(int id) throws ApplicantNotFoundException;
    void deleteAllApplicants();
    List<ApplicantModel> getApplicantByName(String name) throws ApplicantNotFoundException;
    ApplicantModel getApplicantByEmail(String email) throws ApplicantNotFoundException;
    List<ApplicantModel> getApplicantByCourse(CourseModel courseModel) throws ApplicantNotFoundException,CourseNotFoundException;
     String checkCompleted(String email) throws IOException, DocumentException, ApplicantNotFoundException;
}
