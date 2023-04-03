package com.chase.config;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.chase.model.EmpCsv;
import com.chase.writer.FirstItemWriter;

@Configuration
public class EmpDataLoad {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private FirstItemWriter firstItemWriter;
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource datasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public Job empDataLoadJob() {
		return jobBuilderFactory.get("empDataLoadJob")
				.incrementer(new RunIdIncrementer())
				.start(empChunkStep())
				.build();
	}
	

	private Step empChunkStep() {
		return stepBuilderFactory.get("Employee Chunk Step")
				.<EmpCsv, EmpCsv>chunk(1000)
				.reader(flatFileItemReader())
				.writer(jdbcBatchItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor("spring_batch");
	    asyncTaskExecutor.setConcurrencyLimit(5);
	    return asyncTaskExecutor;
	}

	@Bean
	public FlatFileItemReader<EmpCsv> flatFileItemReader(){
		FlatFileItemReader<EmpCsv> flatFileItemReader = new FlatFileItemReader<EmpCsv>();
		flatFileItemReader.setResource(new FileSystemResource(new File("Z:\\MyEclipseWorkspaceMar282023\\wadu-service-hr-1\\src\\main\\resources\\inputfiles\\xyz.csv")));
		flatFileItemReader.setLineMapper(new DefaultLineMapper<EmpCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
					setNames("EmpId", "EmpName", "Salary", "Street", "City", "State");
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<EmpCsv>() {
					{
						setTargetType(EmpCsv.class);
					}
					
				});
			}
		});
		flatFileItemReader.setLinesToSkip(1);
		return flatFileItemReader;
	}
	
	@Bean
	public JdbcBatchItemWriter<EmpCsv> jdbcBatchItemWriter(){
		JdbcBatchItemWriter<EmpCsv> jdbcBatchItemWriter = new JdbcBatchItemWriter<EmpCsv>();
		jdbcBatchItemWriter.setDataSource(datasource());
		jdbcBatchItemWriter.setSql(" insert into employee(empId,empName,salary,street,city,state) "
				+ "values(:empId,:empName,:salary, :street,:city,:state)");
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EmpCsv>());
		
		return jdbcBatchItemWriter;
	}
}
