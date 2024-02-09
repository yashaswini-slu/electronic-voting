package com.techgee.electronicvoting.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyName {
	
	private Long partyNameId;
	private Long partyId;
	private String firstName;
	private String lastName;
	private String middleName;
	@NotNull
	private LocalDate startDate;
	private LocalDate endDate;
	private String restOfName;
	private Boolean isPreferred;
	
	
	//Getters and Setters
	
	@Override
	public String toString() {
		return "PartyName [partyNameId=" + partyNameId + ", partyId=" + partyId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleName=" + middleName + ", startDate=" + startDate + ", endDate="
				+ endDate + ", restOfName=" + restOfName + ", isPreferred=" + isPreferred + "]";
	}
	public Long getPartyNameId() {
		return partyNameId;
	}
	public void setPartyNameId(Long partyNameId) {
		this.partyNameId = partyNameId;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	public String getRestOfName() {
		return restOfName;
	}
	public void setRestOfName(String restOfName) {
		this.restOfName = restOfName;
	}
	public Boolean getIsPreferred() {
		return isPreferred;
	}
	public void setIsPreferred(Boolean isPreferred) {
		this.isPreferred = isPreferred;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, middleName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartyName other = (PartyName) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(middleName, other.middleName);
	}
	
	
	
}
