package com.techgee.electronicvoting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgee.electronicvoting.resource.VoterResource;
import com.techgee.electronicvoting.service.VoterService;
import com.techgee.electronicvoting.shared.Parameters;

@RestController
@RequestMapping("/voter")
public class VoterController {
	
	@Autowired
	VoterService voterService;
	
	@GetMapping(value="list/{loginId}")
	public List<VoterResource> list(@PathVariable(value="loginId") Long loginId) {
		return voterService.listVoters(Parameters.builder().id(loginId).build());
	}
	
	@PostMapping(value="add-voter/{pollId}")
	public boolean addVoter(@PathVariable(value="pollId") Long pollId, @RequestBody VoterResource voterResource) {
		return voterService.addVoter(voterResource, Parameters.builder().id(pollId).build());
	}

}
