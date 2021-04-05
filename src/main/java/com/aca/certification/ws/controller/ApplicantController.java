package com.aca.certification.ws.controller;

import com.aca.certification.core.model.ApplicantModel;
import com.aca.certification.core.model.CourseModel;
import com.aca.certification.core.service.ApplicantService;
import com.aca.certification.core.service.exception.ApplicantNotFoundException;
import com.aca.certification.core.service.exception.CourseNotFoundException;
import com.aca.certification.core.service.exception.ValidationException;
import com.aca.certification.core.service.impl.ApplicantServiceImpl;
import com.aca.certification.infrastructure.entity.Applicant;
import com.aca.certification.infrastructure.repository.ApplicantRepository;
import com.aca.certification.ws.dto.ApplicantDTO;
import com.aca.certification.ws.dto.CourseDTO;
import com.itextpdf.text.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
    private final ApplicantService applicantService;
    private final ModelMapper modelMapper;


    public ApplicantController(ApplicantService applicantService, ModelMapper modelMapper) {
        this.applicantService = applicantService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApplicantDTO> getApplicantById(@PathVariable("id") int id) throws ApplicantNotFoundException {
        ApplicantModel applicant =  this.applicantService.getApplicantById(id);
        ApplicantDTO applicantDTO = modelMapper.map(applicant,ApplicantDTO.class);
        return new ResponseEntity<>(applicantDTO,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApplicantDTO> createToDoList(@Valid @RequestBody ApplicantDTO applicantDTO) throws ValidationException, CourseNotFoundException, ApplicantNotFoundException {
        ApplicantModel applicantModel = modelMapper.map(applicantDTO, ApplicantModel.class);
        ApplicantModel applicantModel1 = applicantService.saveApplicant(applicantModel);
        ApplicantDTO applicantDTO1 = modelMapper.map(applicantModel1,ApplicantDTO.class);
        return new ResponseEntity<>(applicantDTO1,HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApplicantDTO> updateApplicant(@PathVariable("id") int id,
                                                   @Valid @RequestBody ApplicantDTO applicantDTO) throws ApplicantNotFoundException, CourseNotFoundException, ValidationException {
        ApplicantModel applicantModel = modelMapper.map(applicantDTO, ApplicantModel.class);
        ApplicantModel updated = applicantService.updateApplicant(id, applicantModel);
        ApplicantDTO applicantDTO1 = modelMapper.map(updated, ApplicantDTO.class);
        return new ResponseEntity<>(applicantDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(
            @PathVariable("id") int id) throws ApplicantNotFoundException {
        applicantService.deleteApplicantById(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllApplicants() {
        applicantService.deleteAllApplicants();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ApplicantDTO>> getApplicantByName(@PathVariable("name") String name) throws ApplicantNotFoundException {
        List<ApplicantModel> applicants =  this.applicantService.getApplicantByName(name);
        List<ApplicantDTO> applicantDTO = applicants.stream()
                .map(applicantModel -> modelMapper.map(applicantModel,ApplicantDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(applicantDTO,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApplicantDTO> getApplicantByEmail(@PathVariable("email") String email) throws ApplicantNotFoundException {
        ApplicantModel applicants =  this.applicantService.getApplicantByEmail(email);
        ApplicantDTO applicantDTO = modelMapper.map(applicants,ApplicantDTO.class);
        return new ResponseEntity<>(applicantDTO,HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<ApplicantDTO>> getApplicantByName(@Valid @RequestBody CourseDTO courseDTO ) throws ValidationException,ApplicantNotFoundException, CourseNotFoundException {
        CourseModel courseModel = modelMapper.map(courseDTO,CourseModel.class);
        List<ApplicantModel> applicants =  this.applicantService.getApplicantByCourse(courseModel);
        List<ApplicantDTO> applicantDTO = applicants.stream()
                .map(applicantModel -> modelMapper.map(applicantModel,ApplicantDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(applicantDTO,HttpStatus.OK);
    }



}
