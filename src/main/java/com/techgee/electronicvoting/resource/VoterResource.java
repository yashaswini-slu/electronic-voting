package com.techgee.electronicvoting.resource;

public class VoterResource {
	
	private String firstName;
	private String lastName;
	private Long partyId;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	@Override
	public String toString() {
		return "VoterResource [firstName=" + firstName + ", lastName=" + lastName + ", partyId=" + partyId + "]";
	}
	
	

}
