package model;

import java.sql.Date;
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
	@NotNull
	private String FirstName;
	@NotNull
	private String LastName;
	@NotNull
	private Date startDate;
	private Date endDate;
	
	//Getters and Setters
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
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
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
		return "PartyName [partyNameId=" + partyNameId + ", partyId=" + partyId + ", FirstName=" + FirstName
				+ ", LastName=" + LastName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	//Hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(FirstName, LastName);
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
		return Objects.equals(FirstName, other.FirstName) && Objects.equals(LastName, other.LastName);
	}
	
	
}
