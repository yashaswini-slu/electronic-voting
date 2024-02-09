package com.techgee.electronicvoting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password {
	
	private Long passwordId;
	private String scereteKey;
	private Long loginId;
	
	//Getters and Setters
	public Long getPasswordId() {
		return passwordId;
	}
	public void setPasswordId(Long passwordId) {
		this.passwordId = passwordId;
	}
	public String getScereteKey() {
		return scereteKey;
	}
	public void setScereteKey(String scereteKey) {
		this.scereteKey = scereteKey;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	//ToString
	@Override
	public String toString() {
		return "password [passwordId=" + passwordId + ", scereteKey=" + scereteKey + ", loginId=" + loginId + "]";
	}
	
	

}
