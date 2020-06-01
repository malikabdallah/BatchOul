package com.example.demo.batch;


import com.example.demo.batch.modele.Evenement;
import com.example.demo.batch.modele.EvenementItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    public static String date="20-02-2020";

    public static String date2="29-02-2020";
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/reservation");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }


    @Bean
    public JdbcCursorItemReader<Evenement> reader() {
        if(date2==null) {
            JdbcCursorItemReader<Evenement> reader = new JdbcCursorItemReader<Evenement>();
            reader.setDataSource(dataSource);
            reader.setSql("SELECT * FROM evenement WHERE dateevenement =   '" + date + "'");

            reader.setRowMapper(new EvenementRowMapper());


            return reader;
        }else{


            JdbcCursorItemReader<Evenement> reader = new JdbcCursorItemReader<Evenement>();
            reader.setDataSource(dataSource);
            reader.setSql("SELECT * FROM evenement WHERE dateevenement BETWEEN  '" + date + "' AND '"+date2+"'");

            reader.setRowMapper(new EvenementRowMapper());


            return reader;

        }
    }

    public class EvenementRowMapper implements RowMapper<Evenement> {

        @Override
        public Evenement mapRow(ResultSet rs, int rowNum) throws SQLException {
            Evenement evenement=new Evenement();
            evenement.setId(rs.getString(1));
            evenement.setCodeclient(rs.getString(2));
            evenement.setCodesejour(rs.getString(3));
            evenement.setEvenementa(rs.getString(4));
            evenement.setSomme(rs.getString(5));
            evenement.setDateevenement(rs.getString(6));
            evenement.setMethode(rs.getString(7));
            return evenement;
        }

    }

    @Bean
    public EvenementItemProcessor processor() {
        return new EvenementItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<Evenement> writer() {
        FlatFileItemWriter<Evenement> writer = new FlatFileItemWriter<Evenement>();
        writer.setResource(new ClassPathResource("users.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Evenement>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Evenement>() {{
                setNames(new String[]{"id", "codeclient","codesejour",
                "evenementa","somme","dateevenement","methode"});
            }});
        }});

        return writer;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Evenement, Evenement>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportUserJob() {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}