package com.aca.certification.core.service;

import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.infrastructure.entity.Course;

import java.util.List;

public interface CourseService {
    CourseModel getCourseById(int id) throws CourseNotFoundException;
    List<CourseModel> getAllCourses();
    CourseModel saveCourse(CourseModel courseModel) throws ValidationException;
    CourseModel updateCourse(CourseModel courseModel, int id) throws ValidationException, CourseNotFoundException;
    public void deleteCourseByID(int id) throws CourseNotFoundException;
    CourseModel getCourseByName(String name) throws CourseNotFoundException;

}
