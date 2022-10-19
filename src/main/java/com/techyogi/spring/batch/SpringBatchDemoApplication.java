package com.techyogi.spring.batch;

import com.techyogi.spring.batch.service.ReportExportProcessor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;

@SpringBootApplication
@RestController
@RequestMapping("/student/report")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringBatchDemoApplication {

	@Autowired
	ReportExportProcessor reportExportProcessor;

	@GetMapping("/export")
	public String exportReport() {
		// TODO Auto-generated method stub
		try {
			reportExportProcessor.exportReportToPDF();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
		return "Report generated successfully, please check the path";
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemoApplication.class, args);
	}

}
