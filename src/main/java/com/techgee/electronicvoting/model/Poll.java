package com.techgee.electronicvoting.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poll {

	private Long pollId;
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	private Long pprId;
	
	//Getters and Setters
	public Long getPollId() {
		return pollId;
	}
	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Long getPprId() {
		return pprId;
	}
	public void setPprId(Long pprId) {
		this.pprId = pprId;
	}
	@Override
	public String toString() {
		return "Poll [pollId=" + pollId + ", title=" + title + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", pprId=" + pprId + "]";
	}
	
	
}
