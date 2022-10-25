package com.techgee.electronicvoting.resource;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollResource {
	
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	private Long prPrRelationId;
	private String OrganiserName;
	private Long pollId;
	
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
	public Long getPrPrRelationId() {
		return prPrRelationId;
	}
	public void setPrPrRelationId(Long prPrRelationId) {
		this.prPrRelationId = prPrRelationId;
	}
	public String getOrganiserName() {
		return OrganiserName;
	}
	public void setOrganiserName(String organiserName) {
		OrganiserName = organiserName;
	}
	public Long getPollId() {
		return pollId;
	}
	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}
	@Override
	public String toString() {
		return "PollResource [title=" + title + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", prPrRelationId=" + prPrRelationId + ", OrganiserName=" + OrganiserName
				+ ", pollId=" + pollId + "]";
	}
	
}
