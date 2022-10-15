package com.techgee.electronicvoting.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public class ErrorDefinition implements Cloneable {
	
	private String label;
	private int code;
	private ErrorLevel level;
	private ErrorSeverity severity;
	private String message;
	private HttpStatus httpStatus;
	private Throwable cause;
	
	public ErrorDefinition(String label, int code, ErrorLevel level,
			ErrorSeverity severity, String message, HttpStatus httpStatus, Throwable cause) {
		this.label = label;
		this.code = code;
		this.level = level;
		this.severity = severity;
		this.message = message;
		this.httpStatus = httpStatus;
		this.cause = cause;
	}
	
	public ErrorDefinition(){}
	
	/**
	 * @param label
	 * @param code
	 * @param level
	 * @param severity
	 * @param message
	 * @param httpStatus
	 */
	public ErrorDefinition(String label, int code, ErrorLevel level, ErrorSeverity severity, String message,
			HttpStatus httpStatus) {
		super();
		this.label = label;
		this.code = code;
		this.level = level;
		this.severity = severity;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	// TODO Clean this up: printStackTrace() isn't safe, and an error builder should not throw errors or you can wind up with an endless loop
	public ErrorDefinition clone() {
		try {
			return (ErrorDefinition) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ErrorLevel getLevel() {
		return level;
	}

	public void setLevel(ErrorLevel level) {
		this.level = level;
	}

	public ErrorSeverity getSeverity() {
		if(severity == null){
			return ErrorSeverity.NONFATAL;
		}
		return severity;
	}

	public void setSeverity(ErrorSeverity severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorDefinition formatMsg(Object... substitutions) {
		message = MessageFormat.format(message, substitutions);
		return this;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorDefinition [label=" + label + ", code=" + code + ", level=" + level + ", severity=" + severity
				+ ", message=" + message + ", httpStatus=" + httpStatus + ", cause=" + cause + "]";
	}
	
}
