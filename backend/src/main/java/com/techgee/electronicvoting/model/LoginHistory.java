package com.techgee.electronicvoting.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {
	
	private Long loginHistoryId;
	private Long loginId;
	@NotNull
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	//Getters and Setters
	public Long getLoginHistoryId() {
		return loginHistoryId;
	}
	public void setLoginHistoryId(Long loginHistoryId) {
		this.loginHistoryId = loginHistoryId;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	//ToString
	@Override
	public String toString() {
		return "LoginHistory [loginHistoryId=" + loginHistoryId + ", loginId=" + loginId + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
	

}
