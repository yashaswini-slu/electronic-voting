package com.techgee.electronicvoting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgee.electronicvoting.dao.AllowedResponseOptionDao;
import com.techgee.electronicvoting.dao.PollDao;
import com.techgee.electronicvoting.dao.PollQuestionDao;
import com.techgee.electronicvoting.exception.VotingException;
import com.techgee.electronicvoting.model.AllowedResponseOption;
import com.techgee.electronicvoting.model.Poll;
import com.techgee.electronicvoting.model.PollQuestion;
import com.techgee.electronicvoting.resource.PollQuestionOptionResource;
import com.techgee.electronicvoting.shared.Parameters;

@Service
public class PollQuestionService {
	
	@Autowired
	AllowedResponseOptionDao allowedResponseOptionDao;
	
	@Autowired
	PollQuestionDao pollQuestionDao;
	
	@Autowired
	PollDao pollDao;

	public PollQuestionOptionResource create( PollQuestionOptionResource pollQuestionOptionResource,
			Parameters parameters) {
		Poll poll = pollDao.getV1(parameters).orElseThrow(() -> new VotingException("Poll Id does not exist"));
		if(poll.getEndDate().isBefore(LocalDate.now())) {
			throw new VotingException("Poll end date is experied, You cannot add Questions to this Poll");
		}
		validateResource(pollQuestionOptionResource);
		pollQuestionOptionResource.setPollId(poll.getPollId());
		Long pollQuestion = createQuestion(pollQuestionOptionResource);
		createOptions(pollQuestionOptionResource, new Parameters(pollQuestion));
		parameters.setForeignKey(pollQuestion);
		return setPollQuestionResource(parameters);
	}
	
	public List<PollQuestionOptionResource> listQuestions(Parameters parameters) {
		Poll poll = pollDao.getV1(parameters).orElse(null);
		List<PollQuestionOptionResource> resources = new ArrayList<>();
		if(poll !=null) {
			List<PollQuestion> pollQuestion = pollQuestionDao.list(parameters, PollQuestionDao.BY_POLL_ID);
			pollQuestion.forEach(pq -> {
				resources.add(setResource(pq, parameters));
			});
			
		}
		return resources;
	}

	private void validateResource( PollQuestionOptionResource pollQuestionOptionResource) {
		if(pollQuestionOptionResource.getOptions().size() < 2) {
			throw new VotingException("Question should contain atleast 2 options");
		}
		if(pollQuestionOptionResource.getOptions().stream().filter(p->p.isCorrect() == true).count() >= 2) {
			throw new VotingException("Question should contain only one correct answer");
		}
	}
	
	private void createOptions( PollQuestionOptionResource pollQuestionOptionResource, Parameters parameters) {
		List<AllowedResponseOption> options = pollQuestionOptionResource.getOptions();
		options.forEach(op -> op.setPollQuestionId(parameters.getId()));
		allowedResponseOptionDao.insertBatch(options);
	}

	private Long createQuestion(PollQuestionOptionResource pollQuestionOptionResource) {
		PollQuestion pollQuestion = new PollQuestion();
		pollQuestion.setPollQuestion(pollQuestionOptionResource.getQuestion());
		pollQuestion.setPollId(pollQuestionOptionResource.getPollId());
		pollQuestion.setPollQuestionUuid(UUID.randomUUID());
		pollQuestion = pollQuestionDao.create(pollQuestion, null);
		return pollQuestion.getPollQuestionId();
	}
	
	private PollQuestionOptionResource setPollQuestionResource(Parameters parameters) {
		PollQuestion pollQuestion = pollQuestionDao.get(new Parameters(parameters.getForeignKey()));
		return setResource(pollQuestion, parameters);
	}
	
	private PollQuestionOptionResource setResource(PollQuestion pollQuestion, Parameters parameters) {
		PollQuestionOptionResource resource = new PollQuestionOptionResource();
		resource.setQuestion(pollQuestion.getPollQuestion());
		resource.setPollQuestionId(pollQuestion.getPollQuestionId());
		List<AllowedResponseOption> options = allowedResponseOptionDao.list(new Parameters(pollQuestion.getPollQuestionId()), AllowedResponseOptionDao.BY_POLL_QUESTION_ID);
		resource.setOptions(options);
		resource.setPollId(parameters.getId());
		return resource;
	}



}
