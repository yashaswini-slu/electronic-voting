package model;

import java.sql.Date;

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
	private Date startDate;
	@NotNull
	private Date endDate;
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	//ToString
	@Override
	public String toString() {
		return "Poll [pollId=" + pollId + ", title=" + title + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
	
	
}
