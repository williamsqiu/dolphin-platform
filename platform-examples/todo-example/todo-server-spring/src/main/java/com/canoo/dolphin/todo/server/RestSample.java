package com.canoo.dolphin.todo.server;

import com.canoo.platform.server.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestSample {

    @Autowired
    private Security security;

    @RequestMapping(method = RequestMethod.GET)
    String readBookmarks() {
security.requireUser("bla");
        return "Angemeldet: " + security.hasUser();
    }

}
