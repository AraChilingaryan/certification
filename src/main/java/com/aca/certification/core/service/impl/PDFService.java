package com.aca.certification.core.service.impl;

import com.aca.certification.infrastructure.entity.Applicant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

@Service
public class PDFService {
    public void createPDF(Applicant applicant) throws DocumentException, IOException {
        try {
            Document pdfDoc = new Document(PageSize.A4);

            PdfWriter.getInstance(pdfDoc, new FileOutputStream("src/main/resources/" + applicant.getName() + ".pdf"))
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            pdfDoc.open();

            Font myFont = new Font();
            myFont.setStyle(Font.NORMAL);
            myFont.setSize(11);
            pdfDoc.add(new Paragraph("\n"));
            pdfDoc.addTitle("CERTIFICATE\n" +
                    "" +
                    "" +
                    "INTRODUCTION TO COMPUTER SCIENCE");
            pdfDoc.addCreator("Introduction to computer science and the study of algorithms; foundational ideas, computer organization, software concepts (e.g.,\n" +
                    "\n" +
                    "networking, databases, security); programming concepts using Java.");

            pdfDoc.addAuthor("Applicant name - " + applicant.getName());
            pdfDoc.addAuthor("Teacher name - " + applicant.getCourse().getTeacherName());
            pdfDoc.addAuthor("Course duration - " + applicant.getCourse().getStartDate() +
                    "-" + applicant.getCourse().getEndDate());


            BufferedReader br = new BufferedReader(new FileReader(applicant.getName()));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                Paragraph para = new Paragraph(strLine + "\n", myFont);
                para.setAlignment(Element.ALIGN_JUSTIFIED);
                pdfDoc.add(para);
            }

            pdfDoc.close();
            br.close();
        } catch (Exception e) {
            System.out.println();
        }
    }
}