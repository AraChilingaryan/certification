package com.aca.certification.core.service.impl;

import com.aca.certification.core.model.ApplicantModel;
import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.ApplicantService;
import com.aca.certification.core.service.exception.ApplicantNotFoundException;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.infrastructure.entity.Applicant;
import com.aca.certification.infrastructure.entity.Course;
import com.aca.certification.infrastructure.repository.ApplicantRepository;
import com.aca.certification.infrastructure.repository.CourseRepository;
import com.aca.certification.utils.enumaration.Status;
import com.itextpdf.text.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ModelMapper modelMapper, CourseRepository courseRepository) {
        this.applicantRepository = applicantRepository;
        this.modelMapper = modelMapper;
        this.courseRepository = courseRepository;
    }


    @Override
    public ApplicantModel getApplicantById(int id) throws ApplicantNotFoundException {
        Applicant applicant = this.applicantRepository.findById(id).orElseThrow(() -> new ApplicantNotFoundException("applicant not found for this id :: " + id));
        ApplicantModel applicantModel = modelMapper.map(applicant,ApplicantModel.class);
        applicantModel.setCourse_id(applicant.getCourse().getId());
        return applicantModel;
    }

    @Override
    public List<ApplicantModel> getAllApplicants() {
        return this.applicantRepository.findAll()
                .stream()
                .map(applicant -> modelMapper.map(applicant,ApplicantModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ApplicantModel saveApplicant(ApplicantModel applicantModel) throws ValidationException, CourseNotFoundException {
        if (applicantModel == null) {
            throw new ValidationException("applicantModel not found to save");
        }
        Course course = courseRepository.findById(applicantModel.getCourse_id())
                .orElseThrow(() -> new CourseNotFoundException("course specified not found"));
        Applicant applicant = modelMapper.map(applicantModel, Applicant.class);
        applicant.setCourse(course);
        Applicant applicant1 = this.applicantRepository.save(applicant);
        ApplicantModel applicantModel1 = modelMapper.map(applicant,ApplicantModel.class);
        applicantModel1.setCourse_id(applicant.getCourse().getId());
        return applicantModel1;

    }


    @Override
    public ApplicantModel updateApplicant(int id,ApplicantModel applicantModel) throws ApplicantNotFoundException, CourseNotFoundException, ValidationException{
        if (applicantModel != null) {
            Applicant applicantToBeUpdated = applicantRepository.findById(id).orElseThrow(() ->
                    new ApplicantNotFoundException("applicant not found for this id :: " + id));
            Applicant applicant = modelMapper.map(applicantModel, Applicant.class);
            applicant.setId(id);
            Course course = courseRepository.findById(applicantModel.getCourse_id())
                    .orElseThrow(() -> new CourseNotFoundException("course not found"));
            applicant.setCourse(course);
            applicantRepository.save(applicant);
            ApplicantModel applicantModel1 =  modelMapper.map(applicant,ApplicantModel.class);
            applicantModel1.setCourse_id(applicant.getCourse().getId());
            return applicantModel1;
        } else throw new ValidationException("Body Not Valid");
    }

    @Override
    public void deleteApplicantById(int id) throws ApplicantNotFoundException{
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ApplicantNotFoundException("applicant not found for this id :: " + id));
        applicantRepository.delete(applicant);
    }

    @Override
    public void deleteAllApplicants() {
     applicantRepository.deleteAll();
    }

    @Override
    public List<ApplicantModel> getApplicantByName(String name) throws ApplicantNotFoundException {
        List<Applicant> list = applicantRepository.findAllByName(name);
        if(list.isEmpty()) {
            throw new ApplicantNotFoundException("not found applicants for that name :: " + name);
        }
        List<ApplicantModel> list1 =    list.stream()
                .map(applicant -> modelMapper.map(applicant,ApplicantModel.class))
                .collect(Collectors.toList());
        int i = 0;
       for(ApplicantModel applicantModel : list1){
           applicantModel.setCourse_id(list.get(i).getCourse().getId());
           i++;
       }
       return list1;
    }

    @Override
    public ApplicantModel getApplicantByEmail(String email) throws ApplicantNotFoundException {
        Applicant applicant = this.applicantRepository
                .findAllByEmail(email)
                .orElseThrow(() ->new ApplicationContextException("not found applicants for that email ::" + email));
        return modelMapper.map(applicant, ApplicantModel.class);
    }

    @Override
    public List<ApplicantModel> getApplicantByCourse(CourseModel courseModel) throws ApplicantNotFoundException, CourseNotFoundException {
        Course course = this.courseRepository
                .findById(courseModel.getId())
                .orElseThrow(() -> new CourseNotFoundException("not found courses for that id :: " + courseModel.getId()));
        List<ApplicantModel> list =  this.applicantRepository.findAllByCourse(course)
                .stream()
                .map(applicant -> modelMapper.map(applicant,ApplicantModel.class)).collect(Collectors.toList());
          list.forEach(applicantModel -> applicantModel.setCourse_id(course.getId()));
          return list;
    }

    @Autowired
    private  PDFService pdfService;
    @Override
    public String checkCompleted(String email) throws IOException, DocumentException, ApplicantNotFoundException {
        Applicant applicant = applicantRepository.findAllByEmail(email).orElseThrow(() -> new ApplicationContextException("not found applicant for email :" + email));
        if (applicant.getStatus().equals(Status.COMPLETED)) {
            pdfService.createPDF(applicant);
        }
        return applicant.getName();
    }

}
