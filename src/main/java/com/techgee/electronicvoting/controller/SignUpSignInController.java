package com.techgee.electronicvoting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgee.electronicvoting.model.Login;
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
	
	@PostMapping(value = "/signIn")
	public Login signIn(@Valid @RequestBody SignUpSignInResource signUpSignInResource) {
		return signUpSignInService.signIn(signUpSignInResource);
	}
	@PostMapping(value = "/logOut")
	public Login logOut(@Valid @RequestBody SignUpSignInResource signUpSignInResource) {
		return signUpSignInService.signIn(signUpSignInResource);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
	    return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
