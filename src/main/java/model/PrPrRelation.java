package model;

import java.sql.Date;

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
	private Date startDate;
	private Date endDate;
	
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
		return "PrPrRelation [prPrRelationId=" + prPrRelationId + ", partyRoleId1=" + partyRoleId1 + ", partyRoleId2="
				+ partyRoleId2 + ", prPrRelationCd=" + prPrRelationCd + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
	
	

}
