package com.techgee.electronicvoting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgee.electronicvoting.dao.LoginDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.dao.PartyRoleDao;
import com.techgee.electronicvoting.dao.PrPrRelationDao;
import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.model.PrPrRelation;
import com.techgee.electronicvoting.resource.VoterResource;
import com.techgee.electronicvoting.shared.Parameters;

@Service
public class VoterService {
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	PartyNameDao partyNameDao;
	
	@Autowired
	PartyRoleDao partyRoleDao;
	
	@Autowired
	PrPrRelationDao prPrRelationDao;
	
	
	
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
	
	public boolean addVoter(VoterResource voterResource, Parameters parameters) {
		PartyRole partyRole = partyRoleDao.getV1(new Parameters(voterResource.getPartyId(), PartyRole.VOTER_ROLE_CD), PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		if(partyRole == null) {
			partyRole = createPartyRole(new Parameters(voterResource.getPartyId(), PartyRole.VOTER_ROLE_CD));
		}
		return checkAndCreatePrPr(partyRole, parameters);
	}
	
	private VoterResource setVoterResource(PartyName partyName) {
		VoterResource voterResource  = new VoterResource();
		voterResource.setFirstName(partyName.getFirstName());
		voterResource.setLastName(partyName.getLastName());
		voterResource.setPartyId(partyName.getPartyId());
		return voterResource;
	}
	
	private PartyRole createPartyRole(Parameters parameters) {
		PartyRole partyRole = new PartyRole();
		partyRole.setPartyId(parameters.getId());
		partyRole.setPartyRoleCd(parameters.getForeignKey());
		partyRole.setStartDate(LocalDate.now());
		return partyRoleDao.create(partyRole, parameters);
	}
	
	private boolean checkAndCreatePrPr(PartyRole partyRoleVoter, Parameters parameters) {
		PartyRole partyRoleUser = partyRoleDao.getV1(Parameters.builder().id(partyRoleVoter.getPartyId()).foreignKey(PartyRole.USER_ROLE_CD).build(), 
				PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		PrPrRelation prPrRelation = prPrRelationDao.getV1(Parameters.builder().id(partyRoleUser.getPartyRoleId()).
				foreignKey(partyRoleVoter.getPartyRoleId()).parentParameters(new Parameters
						(PrPrRelation.USER_VOTER, parameters.getId())).build(), PrPrRelationDao.BY_ROLES_AND_ROLE_CD_POLL_ID_ENDDATE_NULL).orElse(null);
		if(prPrRelation != null) {
			throw new VotingException("Users is already voter");
		}
		prPrRelation = new PrPrRelation();
		prPrRelation.setPartyRoleId1(partyRoleUser.getPartyRoleId());
		prPrRelation.setPartyRoleId2(partyRoleVoter.getPartyRoleId());
		prPrRelation.setPrPrRelationCd(PrPrRelation.USER_VOTER);
		prPrRelation.setStartDate(LocalDate.now());
		prPrRelation.setPollId(parameters.getId());
		prPrRelation = prPrRelationDao.create(prPrRelation, null);
		return true;
	}

}
