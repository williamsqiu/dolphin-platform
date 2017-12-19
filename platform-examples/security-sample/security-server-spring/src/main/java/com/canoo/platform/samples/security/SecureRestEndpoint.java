package com.canoo.platform.samples.security;

import com.canoo.platform.server.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

@RestController
@RequestMapping("/api/secure/message")
public class SecureRestEndpoint {

    private final static Logger LOG = LoggerFactory.getLogger(SecureRestEndpoint.class);

    @Inject
    SecurityContext securityContext;

    @RequestMapping(method = RequestMethod.GET)
    public String getSecureMessage() {
        final String userName = Optional.ofNullable(securityContext.getUser()).
                map(u -> u.getName()).
                orElse("UNNOWN");
        LOG.info("Secure endpoint called by {}", userName);
        return "A secure message that was requested by " + userName;
    }
}
