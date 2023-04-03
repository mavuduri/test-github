package com.chase.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.chase.model.EmpCsv;

@Component
public class FirstItemWriter implements ItemWriter<EmpCsv>{

	@Override
	public void write(List<? extends EmpCsv> items) throws Exception {
		System.out.println("Inside Item writer");
		items.stream().forEach(System.out::println);
	}

}
