package org.example.companytech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class CompanyTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyTechApplication.class, args);
    }

}
