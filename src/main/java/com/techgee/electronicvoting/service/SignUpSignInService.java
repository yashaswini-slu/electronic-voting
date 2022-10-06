package com.techgee.electronicvoting.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgee.electronicvoting.dao.PartyDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.model.Party;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.resource.SignUpSignInResource;
import com.techgee.electronicvoting.shared.Parameters;

import io.swagger.v3.oas.annotations.Parameter;

@Service
public class SignUpSignInService {
	
	@Autowired
	PartyDao partyDao;
	
	@Autowired
	PartyNameDao partyNameDao;
	
	public boolean signUp(SignUpSignInResource signUpSignInResource) {
		Party party = createParty(); 
		createPartyName(signUpSignInResource, new Parameters(party.getPartyId()));
		createPartyRole(new Parameters(party.getPartyId()));
		return true;
	}

	@Parameter
	/*
	 * parameter id - partyUid
	 * */
	private void createPartyRole(Parameters parameters) {
		PartyRole partyRole = new PartyRole();
		partyRole.setPartyId(parameters.getId());
		partyRole.setPartyRoleCd(PartyRole.USER_ROLE_CD);
		partyRole.setStartDate(LocalDate.now());
	}

	@Parameter
	/*
	 * parameter id - partyUid
	 * */
	private void createPartyName(SignUpSignInResource signUpSignInResource, Parameters parameters) {
		PartyName partyName = new PartyName();
		partyName.setPartyId(parameters.getId());
		partyName.setFirstName(signUpSignInResource.getFirstName());
		partyName.setLastName(signUpSignInResource.getLastName());
		partyName.setStartDate(LocalDate.now());
	}

	private Party createParty() {
		Party party = new Party();
		party.setPartyCd(1L);
		party = partyDao.createV1(party, new Parameters(0L)).orElse(null);
		System.out.printf("Party added   ", party.getPartyId());
		return party;
	}

}
