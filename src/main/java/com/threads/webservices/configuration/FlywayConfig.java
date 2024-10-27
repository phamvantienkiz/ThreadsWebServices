package com.threads.webservices.configuration;

import com.threads.webservices.dto.request.UserCreationRequest;
import com.threads.webservices.entity.User;
import com.threads.webservices.service.UserService;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.locations}")
    private String[] flywayLocations;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    @Bean
    public Flyway flyway() {

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayLocations)
                .baselineOnMigrate(true)//default baseline is V1
                .baselineVersion("1") // baseline 0 đã có
                .load();

        return flyway;
    }


}
