package model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoterResponse {

	private Long voterResponseId;
	private Long prPrRelationId;
	private LocalDateTime castTime;
	private Long allowedResponseOptionId;
	
	//Getters and Setters
	public Long getVoterResponseId() {
		return voterResponseId;
	}
	public void setVoterResponseId(Long voterResponseId) {
		this.voterResponseId = voterResponseId;
	}
	public Long getPrPrRelationId() {
		return prPrRelationId;
	}
	public void setPrPrRelationId(Long prPrRelationId) {
		this.prPrRelationId = prPrRelationId;
	}
	public LocalDateTime getCastTime() {
		return castTime;
	}
	public void setCastTime(LocalDateTime castTime) {
		this.castTime = castTime;
	}
	public Long getAllowedResponseOptionId() {
		return allowedResponseOptionId;
	}
	public void setAllowedResponseOptionId(Long allowedResponseOptionId) {
		this.allowedResponseOptionId = allowedResponseOptionId;
	}
	
	//ToString
	@Override
	public String toString() {
		return "VoterResponse [voterResponseId=" + voterResponseId + ", prPrRelationId=" + prPrRelationId
				+ ", castTime=" + castTime + ", allowedResponseOptionId=" + allowedResponseOptionId + "]";
	}
	
	
	
}
