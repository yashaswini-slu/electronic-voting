package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {
	
	
	private Long partyId;
	private Long partyCdDd;
	private String partyCdLocale;
	
	//Getters and Setters
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getPartyCdDd() {
		return partyCdDd;
	}
	public void setPartyCdDd(Long partyCdDd) {
		this.partyCdDd = partyCdDd;
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
		return "Party [partyId=" + partyId + ", partyCdDd=" + partyCdDd + ", partyCdLocale=" + partyCdLocale + "]";
	}
	
	

}
