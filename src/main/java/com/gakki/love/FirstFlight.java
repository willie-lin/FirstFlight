package com.gakki.love;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/4
 * \* Time: 10:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@ComponentScan("com.gakki.love")
public class FirstFlight extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(FirstFlight.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FirstFlight.class);
    }

}