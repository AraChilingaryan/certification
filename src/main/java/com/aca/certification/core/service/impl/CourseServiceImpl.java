package com.aca.certification.core.service.impl;

import com.aca.certification.core.model.ApplicantModel;
import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.CourseService;
import com.aca.certification.core.service.exception.ApplicantNotFoundException;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.infrastructure.entity.Applicant;
import com.aca.certification.infrastructure.entity.Course;
import com.aca.certification.infrastructure.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CourseModel getCourseById(int id) throws CourseNotFoundException {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found by this id :: " + id));
        CourseModel courseModel = modelMapper.map(course,CourseModel.class);
        Set<Applicant> applicantSet = course.getApplicantSet();
        Set<ApplicantModel> applicantModels = applicantSet.stream()
                .map(applicant -> modelMapper.map(applicant,ApplicantModel.class)).collect(Collectors.toSet());
                applicantModels.forEach(applicantModel -> applicantModel.setCourse_id(course.getId()));
                courseModel.setApplicantSet(applicantModels);
        return courseModel;
    }

    @Override
    public List<CourseModel> getAllCourses() {
        List<CourseModel> courses = this.courseRepository.findAll().stream()
                .map(x -> modelMapper.map(x, CourseModel.class))
                .collect(Collectors.toList());
        return courses;
    }

    @Override
    public CourseModel saveCourse(CourseModel courseModel)  throws ValidationException{
        if (courseModel != null) {
            Course course = modelMapper.map(courseModel, Course.class);
            return modelMapper.map(courseRepository.save(course), CourseModel.class);
        } else throw new ValidationException("Body Not Valid");
    }

    @Override
    public CourseModel updateCourse(CourseModel courseModel,int id) throws ValidationException, CourseNotFoundException {
        if (courseModel != null) {
            Course courseToBeUpdated = courseRepository.findById(id).orElseThrow(() ->
                    new CourseNotFoundException("applicant not found for this id :: " + id));
            Course course = modelMapper.map(courseModel, Course.class);
            course.setId(id);
            Course course1 = this.courseRepository.save(course);
            CourseModel courseModel1 = modelMapper.map(course1,CourseModel.class);
            return courseModel1;

        } else throw new ValidationException("Body Not Valid");
    }

    @Override
    public void deleteCourseByID(int id) throws CourseNotFoundException {
        this.courseRepository.deleteById(id);
    }

    @Override
    public CourseModel getCourseByName(String name) throws CourseNotFoundException{
        Course course = courseRepository.findByName(name)
                .orElseThrow(() -> new CourseNotFoundException("course not found for this id :: " + name));
        CourseModel courseModel = modelMapper.map(course, CourseModel.class);
        courseModel.getApplicantSet().stream().forEach(applicant -> applicant.setCourse_id(course.getId()));
        return courseModel;
    }
}
