package com.techgee.electronicvoting.exception;

import java.security.Timestamp;

public class VotingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorDefinition errorDefinition;
	private Timestamp timestamp;
	
	public VotingException(String message) {
		super(message);
	}
	
	public VotingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public VotingException(ErrorDefinition definition) {
		this.errorDefinition = definition;
	}
	
	public VotingException(ErrorDefinition definition, String message) {
		super(message);
		this.errorDefinition = definition;
		this.errorDefinition.setMessage(message);		
	}

	/**
	 * @return the errorDefinition
	 */
	public ErrorDefinition getErrorDefinition() {
		return errorDefinition;
	}

	/**
	 * @param errorDefinition the errorDefinition to set
	 */
	public void setErrorDefinition(ErrorDefinition errorDefinition) {
		this.errorDefinition = errorDefinition;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VotingException [errorDefinition=" + errorDefinition + ", timestamp=" + timestamp + "]";
	}
	
}
