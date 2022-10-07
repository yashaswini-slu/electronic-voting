package com.techgee.electronicvoting.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techgee.electronicvoting.dao.LoginDao;
import com.techgee.electronicvoting.dao.PartyDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.dao.PartyRoleDao;
import com.techgee.electronicvoting.dao.PasswordDao;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.model.Party;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.model.Password;
import com.techgee.electronicvoting.resource.SignUpSignInResource;
import com.techgee.electronicvoting.shared.Parameters;

import io.swagger.v3.oas.annotations.Parameter;

@Service
public class SignUpSignInService {
	
	@Autowired
	PartyDao partyDao;
	
	@Autowired
	PartyNameDao partyNameDao;
	
	@Autowired
	PartyRoleDao partyRoleDao;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	PasswordDao passwordDao;
	
	@Transactional
	public boolean signUp(SignUpSignInResource signUpSignInResource) {
		//TODO check password, check required data based on type 
		Party party = createParty(); 
		createPartyName(signUpSignInResource, new Parameters(party.getPartyId()));
		createPartyRole(new Parameters(party.getPartyId()));
		Login login = createLogin(signUpSignInResource, new Parameters(party.getPartyId()));
		createPassword(signUpSignInResource, new Parameters(login.getLoginId()));
		return true;
	}

	@Parameter
	/*
	 * parameter id - loginId
	 * */
	private Password createPassword(SignUpSignInResource signUpSignInResource, Parameters parameters) {
		Password password = new Password();
		password.setLoginId(parameters.getId());
		password.setScereteKey(signUpSignInResource.getPassword());
		passwordDao.create(password, parameters);
		return null;
	}

	@Parameter
	/*
	 * parameter id - partyId
	 * */
	private Login createLogin(SignUpSignInResource signUpSignInResource, Parameters parameters) {
		Login login = new Login();
		login.setPartyId(parameters.getId());
		login.setUserId(signUpSignInResource.getUserName());
		login.setStartDate(LocalDate.now());
		login = loginDao.create(login, parameters);
		return login;
		
	}

	@Parameter
	/*
	 * parameter id - partyId
	 * */
	private void createPartyRole(Parameters parameters) {
		PartyRole partyRole = new PartyRole();
		partyRole.setPartyId(parameters.getId());
		partyRole.setPartyRoleCd(PartyRole.USER_ROLE_CD);
		partyRole.setStartDate(LocalDate.now());
		partyRoleDao.create(partyRole, parameters);
	}

	@Parameter
	/*
	 * parameter id - partyId
	 * */
	private void createPartyName(SignUpSignInResource signUpSignInResource, Parameters parameters) {
		PartyName partyName = new PartyName();
		partyName.setPartyId(parameters.getId());
		partyName.setFirstName(signUpSignInResource.getFirstName());
		partyName.setLastName(signUpSignInResource.getLastName());
		partyName.setStartDate(LocalDate.now());
		partyNameDao.create(partyName, parameters);
	}

	private Party createParty() {
		Party party = new Party();
		party.setPartyCd(1L);
		party = partyDao.create(party, new Parameters(0L));
		System.out.printf("Party added   ", party.getPartyId());
		return party;
	}

}