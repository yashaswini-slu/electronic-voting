package com.techgee.electronicvoting.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class AllowedResponseOption {
	
	private Long allowedResponseOptionId;
	private Long pollQuestionId;
	@NotNull
	private String option;
	private Boolean isCorrect;
	
	//Getters and Setters
	public Long getAllowedResponseOptionId() {
		return allowedResponseOptionId;
	}
	public void setAllowedResponseOptionId(Long allowedResponseOptionId) {
		this.allowedResponseOptionId = allowedResponseOptionId;
	}
	public Long getPollQuestionId() {
		return pollQuestionId;
	}
	public void setPollQuestionId(Long pollQuestionId) {
		this.pollQuestionId = pollQuestionId;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public Boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	@Override
	public String toString() {
		return "AllowedResponseOption [allowedResponseOptionId=" + allowedResponseOptionId + ", pollQuestionId="
				+ pollQuestionId + ", option=" + option + ", isCorrect=" + isCorrect + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(isCorrect, option, pollQuestionId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllowedResponseOption other = (AllowedResponseOption) obj;
		return Objects.equals(isCorrect, other.isCorrect) && Objects.equals(option, other.option)
				&& Objects.equals(pollQuestionId, other.pollQuestionId);
	}
	
	
	
	
	
	

}
