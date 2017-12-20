package com.canoo.platform.samples.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class OpenRestEndpoint {

    private final static Logger LOG = LoggerFactory.getLogger(OpenRestEndpoint.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getOpenMessage() {
        LOG.info("Open endpoint called");
        return "An unsecure message";
    }
}
