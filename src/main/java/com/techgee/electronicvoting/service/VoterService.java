package com.techgee.electronicvoting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgee.electronicvoting.dao.LoginDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.resource.VoterResource;
import com.techgee.electronicvoting.shared.Parameters;

@Service
public class VoterService {
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	PartyNameDao partyNameDao;
	
	
	public List<VoterResource> listVoters(Parameters parameter) {
		List<Login> loginUsers = loginDao.list(parameter);
		loginUsers = loginUsers.stream().filter(p-> p.getLoginId() != parameter.getId()).collect(Collectors.toList());
		Set<Long> partyIds = loginUsers.stream().map(Login::getPartyId).collect(Collectors.toSet());
		List<PartyName> partyNames = partyNameDao.listIn(parameter, partyIds, PartyNameDao.BY_PARTYID);
		List<VoterResource> resources = new ArrayList<>();
		partyNames.forEach(partyName -> {
			resources.add(setVoterResource(partyName));
		});
		return resources;
	}
	
	private VoterResource setVoterResource(PartyName partyName) {
		VoterResource voterResource  = new VoterResource();
		voterResource.setFirstName(partyName.getFirstName());
		voterResource.setLastName(partyName.getLastName());
		voterResource.setPartyId(partyName.getPartyId());
		return voterResource;
	}

}
