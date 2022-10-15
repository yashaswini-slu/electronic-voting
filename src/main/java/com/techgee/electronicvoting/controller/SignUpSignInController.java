package com.techgee.electronicvoting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgee.electronicvoting.resource.SignUpSignInResource;
import com.techgee.electronicvoting.service.SignUpSignInService;


@RestController
@RequestMapping("/authorise")
public class SignUpSignInController {
	
	@Autowired
	SignUpSignInService signUpSignInService;
	
	@PostMapping(value = "/signUp")
    public boolean signUp(@Valid @RequestBody SignUpSignInResource signUpSignInResource) {
        return signUpSignInService.signUp(signUpSignInResource);
    }

}
