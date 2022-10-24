package com.techgee.electronicvoting.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgee.electronicvoting.resource.PollResource;
import com.techgee.electronicvoting.service.PollService;
import com.techgee.electronicvoting.shared.Parameters;

@RestController
@RequestMapping("/poll")
public class PollContoller {
	
	@Autowired
	PollService pollService;
	
	@PostMapping(value = "/create/{loginId}")
	public PollResource create(@PathVariable(value = "loginId") Long loginId, @Valid @RequestBody PollResource pollResource) {
		return pollService.create(pollResource, Parameters.builder().id(loginId).build());
	}
	
	@GetMapping(value="get/{pollId}")
	public PollResource get(@PathVariable(value="pollId") Long pollId) {
		return pollService.get(Parameters.builder().id(pollId).build());
	}
	
	@GetMapping(value="list/{loginId}")
	public List<PollResource> list(@PathVariable(value="loginId") Long loginId) {
		return pollService.list(Parameters.builder().id(loginId).build());
	}

}
