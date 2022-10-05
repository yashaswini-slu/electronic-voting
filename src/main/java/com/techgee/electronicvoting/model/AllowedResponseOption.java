package com.techgee.electronicvoting.model;

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
	
	//ToString
	@Override
	public String toString() {
		return "AllowedResponseOption [allowedResponseOptionId=" + allowedResponseOptionId + ", pollQuestionId="
				+ pollQuestionId + ", option=" + option + "]";
	}
	
	

}
