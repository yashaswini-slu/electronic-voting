package com.techgee.electronicvoting.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgee.electronicvoting.dao.LoginDao;
import com.techgee.electronicvoting.dao.PartyNameDao;
import com.techgee.electronicvoting.dao.PartyRoleDao;
import com.techgee.electronicvoting.dao.PollDao;
import com.techgee.electronicvoting.dao.PrPrRelationDao;
import com.techgee.electronicvoting.dao.VoterResponseDao;
import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.model.PartyName;
import com.techgee.electronicvoting.model.PartyRole;
import com.techgee.electronicvoting.model.Poll;
import com.techgee.electronicvoting.model.PrPrRelation;
import com.techgee.electronicvoting.model.VoterResponse;
import com.techgee.electronicvoting.resource.PollQuestionOptionResource;
import com.techgee.electronicvoting.resource.PollResource;
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
	
	@Autowired
	PollDao pollDao;
	
	@Autowired
	VoterResponseDao voterResponseDao;
	
	/*
	 * @param parameter - id: loginId
	 * */
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
	
	/*
	 * @param parameter - id: pollId
	 * */
	public boolean addVoter(VoterResource voterResource, Parameters parameters) {
		PartyRole partyRole = partyRoleDao.getV1(new Parameters(voterResource.getPartyId(), PartyRole.VOTER_ROLE_CD), PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		if(partyRole == null) {
			partyRole = createPartyRole(new Parameters(voterResource.getPartyId(), PartyRole.VOTER_ROLE_CD));
		}
		return checkAndCreatePrPr(partyRole, parameters);
	}
	
	/*
	 * @param parameter - id: loginId
	 * */
	public List<PollResource> listPollToCastVote(Parameters parameters) {
		Login login = loginDao.getV1(parameters).orElseThrow(() -> new VotingException("Login User does not exist"));
		PartyRole partyRoleVoter = partyRoleDao.getV1(new Parameters(login.getPartyId(), PartyRole.VOTER_ROLE_CD), PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElseThrow(() -> new VotingException("There is no Poll to cast vote"));
		PartyRole partyRoleUser = partyRoleDao.getV1(Parameters.builder().id(partyRoleVoter.getPartyId()).foreignKey(PartyRole.USER_ROLE_CD).build(), 
				PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		List<PrPrRelation> prPrRelation = prPrRelationDao.list(Parameters.builder().id(partyRoleUser.getPartyRoleId()).
				foreignKey(partyRoleVoter.getPartyRoleId()).parentParameters(new Parameters
						(PrPrRelation.USER_VOTER)).build(), PrPrRelationDao.BY_ROLES_AND_ROLE_CD_ENDDATE_NULL);
		Set<Long> pollIds = prPrRelation.stream().map(PrPrRelation::getPollId).collect(Collectors.toSet());
		List<Poll> polls = pollDao.listIn(null, pollIds);
		List<PollResource> pollResources = new ArrayList<>();
		polls.forEach(poll -> {
			pollResources.add(setPollResource(poll));
		});
		return pollResources;
	}
	
	/*
	 * @param parameter - id: loginId
	 *         - foreign key: pollId
	 * */
	public boolean castVote(List<PollQuestionOptionResource> pollQuestionOptionResources, Parameters parameters) {
		Login login = loginDao.getV1(parameters).orElseThrow(() -> new VotingException("Login User does not exist"));
		PartyRole partyRoleVoter = partyRoleDao.getV1(new Parameters(login.getPartyId(), PartyRole.VOTER_ROLE_CD), PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElseThrow(() -> new VotingException("User does not has permission to cast vote"));
		PartyRole partyRoleUser = partyRoleDao.getV1(Parameters.builder().id(partyRoleVoter.getPartyId()).foreignKey(PartyRole.USER_ROLE_CD).build(), 
				PartyRoleDao.BY_PARTYID_AND_PARTYROLECD).orElse(null);
		PrPrRelation prPrRelation = prPrRelationDao.getV1(Parameters.builder().id(partyRoleUser.getPartyRoleId()).
				foreignKey(partyRoleVoter.getPartyRoleId()).parentParameters(new Parameters
						(PrPrRelation.USER_VOTER, parameters.getForeignKey())).build(), PrPrRelationDao.BY_ROLES_AND_ROLE_CD_POLL_ID_ENDDATE_NULL).orElseThrow(() -> new VotingException("User does not has permission to cast vote"));
		if(parameters.getForeignKey() != prPrRelation.getPollId()) {
			throw new VotingException("");
		}
		return setAndCreateVoteResponse(pollQuestionOptionResources, new Parameters(prPrRelation.getPrPrRelationId()));
	}
	
	/*
	 * @param parameter - id: prPrRelationId
	 * */
	private boolean setAndCreateVoteResponse(List<PollQuestionOptionResource> pollQuestionOptionResources, Parameters parameters) {
		List<VoterResponse> voterResponseList = new ArrayList<>();
		for(PollQuestionOptionResource resource : pollQuestionOptionResources) {
			VoterResponse voterResponse = new VoterResponse();
			setVoterResponse(voterResponse, resource, parameters);
			voterResponseList.add(voterResponse);
		}
		voterResponseDao.insertBatch(voterResponseList);
		return true;
		//we have get poll id from prPrRelation to get the result
	}
	
	/*
	 * @param parameter - id: prPrRelationId
	 * */
	private void setVoterResponse(VoterResponse voterResponse, PollQuestionOptionResource resource, Parameters parameters) {
		voterResponse.setAllowedResponseOptionId(resource.getResponseOptionId());
		voterResponse.setPrPrRelationId(parameters.getId());
		voterResponse.setCastTime(LocalDateTime.now());
	}

	
	private PollResource setPollResource(Poll poll) {
		PollResource pollResource = new PollResource();
		pollResource.setTitle(poll.getTitle());
		pollResource.setDescription(poll.getDescription());
		pollResource.setStartDate(poll.getStartDate());
		pollResource.setEndDate(poll.getEndDate());
		pollResource.setPollId(poll.getPollId());
		pollResource.setPrPrRelationId(poll.getPprId());
		return pollResource;
	}

	
	private VoterResource setVoterResource(PartyName partyName) {
		VoterResource voterResource  = new VoterResource();
		voterResource.setFirstName(partyName.getFirstName());
		voterResource.setLastName(partyName.getLastName());
		voterResource.setPartyId(partyName.getPartyId());
		return voterResource;
	}
	
	/*
	 * @param parameter - id: partyId
	 * */
	private PartyRole createPartyRole(Parameters parameters) {
		PartyRole partyRole = new PartyRole();
		partyRole.setPartyId(parameters.getId());
		partyRole.setPartyRoleCd(parameters.getForeignKey());
		partyRole.setStartDate(LocalDate.now());
		return partyRoleDao.create(partyRole, parameters);
	}
	
	/*
	 * @param parameter - id: pollId
	 * */
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
