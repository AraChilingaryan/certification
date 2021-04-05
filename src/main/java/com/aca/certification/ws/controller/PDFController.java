package com.aca.certification.ws.controller;

import com.aca.certification.core.service.ApplicantService;
import com.aca.certification.core.service.exception.ApplicantNotFoundException;
import com.aca.certification.core.service.impl.PDFService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Pdf generacnelu processna, bayc xndira talis minchev verj chi
 */
@Controller
@RequestMapping
public class PDFController {

    @Autowired
    private ApplicantService applicantService;

    @RequestMapping(value = "/pdf/{email}",method = RequestMethod.GET,produces = "application/pdf")
    public ResponseEntity<String> getPDF(@PathVariable(name = "email") String email) throws ApplicantNotFoundException, IOException, DocumentException {
        String fileName = applicantService.checkCompleted(email);
        return ResponseEntity.status(HttpStatus.CREATED).body("http://localhost:8080/" + fileName + ".pdf");
    }
}