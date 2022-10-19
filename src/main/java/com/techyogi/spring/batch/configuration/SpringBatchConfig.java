package com.techyogi.spring.batch.configuration;

import com.techyogi.spring.batch.model.StudentReport;
import com.techyogi.spring.batch.service.ReportExportProcessor;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.JAXBException;

/*@Configuration
@EnableBatchProcessing
@AllArgsConstructor*/
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    //@Bean
    public ItemReader<StudentReport> reader() throws JAXBException {
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(StudentReport.class);
        return new StaxEventItemReaderBuilder<StudentReport>()
                .name("studentReportReader")
                .resource(new ClassPathResource("statement-xml/student.xml"))
                .addFragmentRootElements("students")
                .unmarshaller(studentMarshaller)
                .build();
    }

    //@Bean
    public ReportExportProcessor processor() {
        return new ReportExportProcessor();
    }

    public ItemWriter<StudentReport> writer() {
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        StaxEventItemWriter<StudentReport> writer = new StaxEventItemWriter<>();
        writer.setResource(new ClassPathResource("jrxml/student_report.jrxml"));
        writer.setMarshaller(studentMarshaller);
        writer.setRootTagName("students");
        return writer;
    }

    //@Bean
    /*public Step step1() throws JAXBException {
        return stepBuilderFactory.get("step1")
                .<StudentReport, StudentReport>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }*/

    //@Bean
    /*public Job runJob() throws JAXBException {
        return jobBuilderFactory.get("runJob")
                .flow(step1())
                .end()
                .build();
    }*/
}
