package com.promineotech.petstore;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestContoller;

@RestController
public class Controller {
    @GetMapping("/api/test/{resourceId}")
    public String getString(@PathVariable String resourceId) {
        return "Here is the requested resource: " + resourceId;
    }

    @PostMapping("/api/setProfile")
    public void receiveData(@RequestBody Profile profile) {
        System.out.println("Here is the requested profile: " + profile);
    }
}
