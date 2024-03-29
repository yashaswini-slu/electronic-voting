package com.techgee.electronicvoting.model;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollQuestion {
	
	private Long pollQuestionId;
	@NotNull
	private String pollQuestion;
	private UUID pollQuestionUuid;
	private Long pollId;
	
	//Getters and Setters
	public Long getPollQuestionId() {
		return pollQuestionId;
	}
	public void setPollQuestionId(Long pollQuestionId) {
		this.pollQuestionId = pollQuestionId;
	}
	public String getPollQuestion() {
		return pollQuestion;
	}
	public void setPollQuestion(String pollQuestion) {
		this.pollQuestion = pollQuestion;
	}
	public UUID getPollQuestionUuid() {
		return pollQuestionUuid;
	}
	public void setPollQuestionUuid(UUID pollQuestionUuid) {
		this.pollQuestionUuid = pollQuestionUuid;
	}
	public Long getPollId() {
		return pollId;
	}
	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}
	
	//ToString
	@Override
	public String toString() {
		return "PollQuestion [pollQuestionId=" + pollQuestionId + ", pollQuestion=" + pollQuestion
				+ ", pollQuestionUuid=" + pollQuestionUuid + ", pollId=" + pollId + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(pollId, pollQuestion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollQuestion other = (PollQuestion) obj;
		return Objects.equals(pollId, other.pollId) && Objects.equals(pollQuestion, other.pollQuestion);
	}
	
	
	
	

}
