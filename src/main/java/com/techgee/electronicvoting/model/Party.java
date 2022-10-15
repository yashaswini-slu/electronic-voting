package com.techgee.electronicvoting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {
	
	
	private Long partyId;
	private Long partyCd;
	private String partyCdLocale;
	
	//Getters and Setters
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getPartyCd() {
		return partyCd;
	}
	public void setPartyCd(Long partyCd) {
		this.partyCd = partyCd;
	}
	public String getPartyCdLocale() {
		return partyCdLocale;
	}
	public void setPartyCdLocale(String partyCdLocale) {
		this.partyCdLocale = partyCdLocale;
	}
	

	//ToString
	@Override
	public String toString() {
		return "Party [partyId=" + partyId + ", partyCdDd=" + partyCd + ", partyCdLocale=" + partyCdLocale + "]";
	}
	
	

}
