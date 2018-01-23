package com.canoo.platform.samples.security;

import com.canoo.platform.server.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/secure/message")
public class SecureRestEndpoint {

    private final static Logger LOG = LoggerFactory.getLogger(SecureRestEndpoint.class);

    @Autowired
    SecurityContext securityContext;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public String convert(String message) {
        final String userName = Optional.ofNullable(securityContext.getUser()).
                map(u -> u.getName()).
                orElse("UNKNOWN");
        LOG.info("Secure endpoint called by {}", userName);
        return userName.toUpperCase();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSecureMessage() {
        final String userName = Optional.ofNullable(securityContext.getUser()).
                map(u -> u.getName()).
                orElse("UNKNOWN");
        final String convertedUser = restTemplate.postForEntity("http://localhost:8080/api/secure/message", userName, String.class).getBody();
        LOG.info("Secure endpoint called by {}", userName);
        return "A secure message that was requested by " + convertedUser;
    }
}
