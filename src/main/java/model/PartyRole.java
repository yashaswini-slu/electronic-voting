package model;

import java.sql.Date;

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
	private Date startDate;
	private Date endDate;
	
	
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
		return "PartyRole [partyRoleId=" + partyRoleId + ", partyId=" + partyId + ", partyRoleCd=" + partyRoleCd
				+ ", partyRoleCdLocale=" + partyRoleCdLocale + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}
	
	
}
