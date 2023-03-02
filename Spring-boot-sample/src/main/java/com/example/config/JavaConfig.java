package com.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.form.RecordValueReader;

@Configuration
public class JavaConfig {

  @Bean
  public ModelMapper mapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().addValueReader(new RecordValueReader());
    return mapper;
  }
}
