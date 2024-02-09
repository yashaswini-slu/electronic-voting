package com.techgee.electronicvoting.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {
	
	private Long LoginId;
	@NotNull
	private Long partyId;
	@NotNull
	private String userId;
	@NotNull
	private LocalDate startDate;
	private LocalDate endDate;
	
	//Getters and Setters
	public Long getLoginId() {
		return LoginId;
	}
	public void setLoginId(Long loginId) {
		LoginId = loginId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	@Override
	public String toString() {
		return "Login [LoginId=" + LoginId + ", partyId=" + partyId + ", userId=" + userId + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	

	
}
