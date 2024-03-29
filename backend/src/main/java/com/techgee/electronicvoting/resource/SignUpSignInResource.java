package com.techgee.electronicvoting.resource;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpSignInResource {
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String restOfName;
	@NotNull
	private String userName; //Email address
	@NotNull
	private String password;
	private String confirmPassword;
	@NotNull
	@Default
	private boolean isIndividual = true;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isIndividual() {
		return isIndividual;
	}
	public void setIndividual(boolean isIndividual) {
		this.isIndividual = isIndividual;
	}
	
	public String getRestOfName() {
		return restOfName;
	}
	public void setRestOfName(String restOfName) {
		this.restOfName = restOfName;
	}
	@Override
	public String toString() {
		return "SignUpSignInResource [firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName
				+ ", restOfName=" + restOfName + ", userName=" + userName + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", isIndividual=" + isIndividual + "]";
	}
	
	
	
	

}
