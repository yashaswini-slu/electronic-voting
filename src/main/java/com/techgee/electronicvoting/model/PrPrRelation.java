package com.techgee.electronicvoting.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrPrRelation {
	
	private Long prPrRelationId;
	private Long partyRoleId1; //Left partyRole
	private Long partyRoleId2; //Right partyRole
	private Long prPrRelationCd;
	private LocalDate startDate;
	private LocalDate endDate;
	
	//All Cd values
	public static final Long USER_ORGANISE = 1L;
	public static final Long USER_VOTER = 2L;
	public static final Long SERVICE_PROVIDER_USER = 3L;
	
	//Getters and Setters
	public Long getPrPrRelationId() {
		return prPrRelationId;
	}
	public void setPrPrRelationId(Long prPrRelationId) {
		this.prPrRelationId = prPrRelationId;
	}
	public Long getPartyRoleId1() {
		return partyRoleId1;
	}
	public void setPartyRoleId1(Long partyRoleId1) {
		this.partyRoleId1 = partyRoleId1;
	}
	public Long getPartyRoleId2() {
		return partyRoleId2;
	}
	public void setPartyRoleId2(Long partyRoleId2) {
		this.partyRoleId2 = partyRoleId2;
	}
	public Long getPrPrRelationCd() {
		return prPrRelationCd;
	}
	public void setPrPrRelationCd(Long prPrRelationCd) {
		this.prPrRelationCd = prPrRelationCd;
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
		return "PrPrRelation [prPrRelationId=" + prPrRelationId + ", partyRoleId1=" + partyRoleId1 + ", partyRoleId2="
				+ partyRoleId2 + ", prPrRelationCd=" + prPrRelationCd + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
	
	

}
