package com.techgee.electronicvoting.resource;

import java.util.List;

import com.techgee.electronicvoting.model.AllowedResponseOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollQuestionOptionResource {
	
	private String question;
	private List<AllowedResponseOption> options;
	private Long pollId;
	private Long pollQuestionId;
	private Long responseOptionId;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<AllowedResponseOption> getOptions() {
		return options;
	}
	public void setOptions(List<AllowedResponseOption> options) {
		this.options = options;
	}
	public Long getPollId() {
		return pollId;
	}
	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}
	public Long getPollQuestionId() {
		return pollQuestionId;
	}
	public void setPollQuestionId(Long pollQuestionId) {
		this.pollQuestionId = pollQuestionId;
	}
	public Long getResponseOptionId() {
		return responseOptionId;
	}
	public void setResponseOptionId(Long responseOptionId) {
		this.responseOptionId = responseOptionId;
	}
	@Override
	public String toString() {
		return "PollQuestionOptionResource [question=" + question + ", options=" + options + ", pollId=" + pollId
				+ ", pollQuestionId=" + pollQuestionId + ", responseOptionId=" + responseOptionId + "]";
	}
	
	
	
	
	

}
