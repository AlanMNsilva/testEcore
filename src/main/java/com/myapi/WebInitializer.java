/**
 * Created by : Alan Nascimento on 12/09/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer {
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootSecurityJwtApplication.class);
    }
}
