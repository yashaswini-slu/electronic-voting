package com.techgee.electronicvoting.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyRole {
	
	private Long partyRoleId;
	private Long partyId;
	private Long partyRoleCd;
	private String partyRoleCdLocale;
	private LocalDate startDate;
	private LocalDate endDate;
	
	//All Cd values
	public static final Long USER_ROLE_CD = 1L;
	public static final Long ORGANISER_ROLE_CD = 2L;
	public static final Long VOTER_ROLE_CD = 3L;
	
	//Getters and Setters
	public Long getPartyRoleId() {
		return partyRoleId;
	}
	public void setPartyRoleId(Long partyRoleId) {
		this.partyRoleId = partyRoleId;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getPartyRoleCd() {
		return partyRoleCd;
	}
	public void setPartyRoleCd(Long partyRoleCd) {
		this.partyRoleCd = partyRoleCd;
	}
	public String getPartyRoleCdLocale() {
		return partyRoleCdLocale;
	}
	public void setPartyRoleCdLocale(String partyRoleCdLocale) {
		this.partyRoleCdLocale = partyRoleCdLocale;
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
	
	//ToString
	@Override
	public String toString() {
		return "PartyRole [partyRoleId=" + partyRoleId + ", partyId=" + partyId + ", partyRoleCd=" + partyRoleCd
				+ ", partyRoleCdLocale=" + partyRoleCdLocale + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}
	
	
}
