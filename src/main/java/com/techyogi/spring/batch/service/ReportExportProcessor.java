package com.techyogi.spring.batch.service;

import com.techyogi.spring.batch.model.Student;
import com.techyogi.spring.batch.model.StudentReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportExportProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ReportExportProcessor.class);

    @Value("${statement.xml.location}")
    private String studentXmlPath;
    @Value("${statement.jrxml.location}")
    private String studentJrXmlPath;
    @Value("${statement.pdf.location}")
    private String pdfPath;

    public void exportReportToPDF() throws JAXBException, JRException {
        logger.info("Processing student report >>>>>>>>");

        JAXBContext jaxbContext = JAXBContext.newInstance(StudentReport.class);
        StudentReport studentReport = null;
        File stdXmlPath = new File(studentXmlPath);
        File [] files = stdXmlPath.listFiles();
        File jrFile = new File(studentJrXmlPath);
        
        for (File file : files) {
            logger.info("Processing student report >>>>>>>> {}", file.getName());
            if (file.isFile()) {

                studentReport = (StudentReport) jaxbContext.createUnmarshaller().unmarshal(file);
                List<Student> students = studentReport.getStudent();
                JasperReport jasperReport = JasperCompileManager.compileReport(jrFile.getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "Techyogi");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath + file.getName().replace(".xml", ".pdf"));

                logger.info("Student report generated successfully in path: [{}]", pdfPath);
            }else {
                logger.info("File Not Found In Path: [{}]", studentXmlPath);
            }
        }
    }

}
