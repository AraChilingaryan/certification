package com.aca.certification.ws.controller;


import com.aca.certification.core.model.ApplicantModel;
import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.CourseService;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.NotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.core.service.impl.CourseServiceImpl;
import com.aca.certification.infrastructure.entity.Applicant;
import com.aca.certification.infrastructure.entity.Course;
import com.aca.certification.infrastructure.repository.CourseRepository;
import com.aca.certification.ws.dto.ApplicantDTO;
import com.aca.certification.ws.dto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    public CourseController(CourseService courseService ,ModelMapper modelMapper1) {
        this.modelMapper = modelMapper1;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> list = this.courseService.getAllCourses().stream()
                .map(x -> modelMapper.map(x, CourseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable("id") int id) throws CourseNotFoundException {
        CourseModel courseModel =  this.courseService.getCourseById(id);
        CourseDTO courseDTO = modelMapper.map(courseModel,CourseDTO.class);
        return new ResponseEntity<>(courseDTO,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) throws ValidationException {
        CourseModel createdCourseModel = modelMapper.map(courseDTO, CourseModel.class);
        CourseDTO courseDTO1 = modelMapper.map(courseService.saveCourse(createdCourseModel),CourseDTO.class);
        return new ResponseEntity<>(courseDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable("id") int id,
                                              @Valid @RequestBody CourseDTO courseDTO) throws ValidationException,NotFoundException {

        CourseModel updatedCourserModel = modelMapper.map(courseDTO, CourseModel.class);
        CourseDTO courseDTO1 = modelMapper.map(courseService.updateCourse(updatedCourserModel,id),CourseDTO.class);
        return new ResponseEntity<>(courseDTO1, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CourseDTO> getCourseByName(@PathVariable("name") String name) throws CourseNotFoundException{
        CourseModel courseModel = this.courseService.getCourseByName(name);
        CourseDTO courseDTO = modelMapper.map(courseModel,CourseDTO.class);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") int id) throws CourseNotFoundException {
        courseService.deleteCourseByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
