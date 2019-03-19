package com.bb.auth.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class UserController {

    @RequestMapping("/me")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if(principal != null && principal.getName() != null){
            map.put("name", principal.getName());
        }else{
            map.put("info","login please");
        }

        return map;
    }

}
