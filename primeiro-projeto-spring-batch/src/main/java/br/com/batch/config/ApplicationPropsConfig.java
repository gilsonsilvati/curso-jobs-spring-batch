package br.com.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ApplicationPropsConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer configurer() {

        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();

        propertySources.setLocation(
                new FileSystemResource("/etc/config/primeiro-projeto-spring-batch/application.properties")
        );

        return propertySources;
    }
}
