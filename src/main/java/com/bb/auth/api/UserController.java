package com.bb.auth.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Principal me(Principal principal) {
        return principal;
    }

}
