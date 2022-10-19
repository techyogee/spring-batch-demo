package com.techyogi.spring.batch.conmon;

import com.techyogi.spring.batch.model.StudentReport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public class CommonUtils {


    public static StudentReport marshalStudentReport(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StudentReport.class);
        return (StudentReport) jaxbContext.createUnmarshaller().unmarshal(new File(xml));
    }

    public static File [] getFiles(String path) {
        File folder = new File(path);
        return folder.listFiles();
    }
}
