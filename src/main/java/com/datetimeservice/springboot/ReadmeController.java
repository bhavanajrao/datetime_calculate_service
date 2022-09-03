package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReadmeController {
    @RequestMapping(value="/")
    public String index() {
        Logger logger = LoggerFactory.getLogger(ReadmeController.class);
        logger.info("Displaying README page");
        return "README.html";
    }
}
