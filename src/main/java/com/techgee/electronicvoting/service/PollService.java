package com.techgee.electronicvoting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techgee.electronicvoting.dao.LoginDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.dao.PartyRoleDao;
import com.techgee.electronicvoting.dao.PollDao;
import com.techgee.electronicvoting.dao.PrPrRelationDao;
import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.model.Poll;
import com.techgee.electronicvoting.model.PrPrRelation;
import com.techgee.electronicvoting.resource.PollResource;
import com.techgee.electronicvoting.shared.Parameters;

@Service
public class PollService {
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	PollDao pollDao;
	
	@Autowired
	PartyRoleDao partyRoleDao;
	
	@Autowired
	PrPrRelationDao prPrRelationDao;
	
	@Autowired
	PartyNameDao partyNameDao;
	
	@Transactional
	public PollResource create(PollResource pollResource, Parameters parameter) {
		Login login = loginDao.getV1(parameter).orElseThrow(() -> new VotingException("Login User does not exist"));
		validateResource(pollResource);
		Long prPrRelationId = getRelationId(login.getPartyId());
		return createPoll(pollResource, new Parameters(prPrRelationId, login.getPartyId()));
	}

	private PollResource createPoll(PollResource pollResource, Parameters parameters) {
		Poll poll = new Poll();
		poll.setTitle(pollResource.getTitle());
		poll.setDescription(pollResource.getDescription());
		poll.setStartDate(pollResource.getStartDate());
		poll.setEndDate(pollResource.getEndDate());
		poll.setPprId(parameters.getId());
		poll = pollDao.create(poll, parameters);
		return setPollResource(poll, parameters);
	}

	private PollResource setPollResource(Poll poll, Parameters parameters) {
		PollResource pollResource = new PollResource();
		pollResource.setTitle(poll.getTitle());
		pollResource.setDescription(poll.getDescription());
		pollResource.setStartDate(poll.getStartDate());
		pollResource.setEndDate(poll.getEndDate());
		pollResource.setPollId(poll.getPollId());
		pollResource.setPrPrRelationId(poll.getPprId());
		pollResource.setOrganiserName(getOrganiserName(Parameters.builder().id(parameters.getForeignKey()).build()));
		return pollResource;
	}

	private String getOrganiserName(Parameters parameters) {
		PartyName partyName = partyNameDao.get(parameters, PartyNameDao.BY_PARTYID);
		return partyName.getFirstName().concat(" ").concat(partyName.getLastName());
	}

	@Transactional
	private Long getRelationId(Long partyId) {
		PartyRole partyRoleUser = partyRoleDao.getV1(Parameters.builder().id(partyId).foreignKey(PartyRole.USER_ROLE_CD).build(), 
				PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		PartyRole partyRoleOrganiser = partyRoleDao.getV1(Parameters.builder().id(partyId).foreignKey(PartyRole.ORGANISER_ROLE_CD).build(), 
				PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		if(partyRoleOrganiser == null) {
			partyRoleOrganiser = createPartyRole(partyId);
		}

		PrPrRelation prPrRelation = prPrRelationDao.get(Parameters.builder().id(partyRoleUser.getPartyRoleId()).
				foreignKey(partyRoleOrganiser.getPartyRoleId()).parentParameters(new Parameters
						(PrPrRelation.USER_ORGANISE)).build(), PrPrRelationDao.BY_ROLES_AND_ROLE_CD_ENDDATE_NULL);
		if(prPrRelation == null) {
			prPrRelation = new PrPrRelation();
			prPrRelation.setPartyRoleId1(partyRoleUser.getPartyRoleId());
			prPrRelation.setPartyRoleId2(partyRoleOrganiser.getPartyRoleId());
			prPrRelation.setPrPrRelationCd(PrPrRelation.USER_ORGANISE);
			prPrRelation.setStartDate(LocalDate.now());
			prPrRelation = prPrRelationDao.create(prPrRelation, null);
		}
		return prPrRelation.getPrPrRelationId();
	}

	private PartyRole createPartyRole(Long partyId) {
		PartyRole partyRole = new PartyRole();
		partyRole.setPartyId(partyId);
		partyRole.setPartyRoleCd(PartyRole.ORGANISER_ROLE_CD);
		partyRole.setStartDate(LocalDate.now());
		return partyRoleDao.create(partyRole, null);
	}

	private void validateResource(PollResource pollResource) {
		if(pollResource.getEndDate().isBefore(pollResource.getStartDate()) || pollResource.getEndDate().equals(pollResource.getStartDate())) {
			throw new VotingException("End date should be after Start date");
		}
	}

	public PollResource get(Parameters parameters) {
		Poll poll = pollDao.get(parameters);
		return setPollResource(poll, Parameters.builder().foreignKey(getPartyUid(new Parameters(poll.getPprId()))).build());
	}

	private Long getPartyUid(Parameters parameters) {
		PrPrRelation relation = prPrRelationDao.get(parameters);
		PartyRole roleOrganiser = partyRoleDao.get(new Parameters(relation.getPartyRoleId2()));
		return roleOrganiser.getPartyId();
	}

	public List<PollResource> list(Parameters parameters) {
		Login login = loginDao.getV1(parameters).orElseThrow(() -> new VotingException("Login User does not exist"));
		Long prPrRelationId = getRelationId(login.getPartyId());
		List<Poll> polls = pollDao.list(new Parameters(prPrRelationId), PollDao.BY_PPR_ID);
		List<PollResource> pollResources = new ArrayList<>();
		String organiseName = getOrganiserName(Parameters.builder().id(login.getPartyId()).build());
		setPollResources(pollResources, polls, Parameters.builder().word(organiseName).build());
		return pollResources;
	}

	private void setPollResources(List<PollResource> pollResources, List<Poll> polls, Parameters parameters) {
		for(Poll poll : polls) {
			PollResource pollResource = new PollResource();
			pollResource.setOrganiserName(parameters.getWord());
			pollResource.setDescription(poll.getDescription());
			pollResource.setEndDate(poll.getEndDate());
			pollResource.setPollId(poll.getPollId());
			pollResource.setPrPrRelationId(poll.getPprId());
			pollResource.setStartDate(poll.getStartDate());
			pollResource.setTitle(poll.getTitle());
			pollResources.add(pollResource);
		}
		
	}

}
