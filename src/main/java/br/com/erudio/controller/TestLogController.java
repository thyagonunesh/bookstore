package br.com.erudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {

    private final Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @GetMapping("/test")
    public String testLog() {
        logger.debug("This is a debug message");
        logger.info("This is a info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");

        return "Logs generated successfully!";
    }

}
