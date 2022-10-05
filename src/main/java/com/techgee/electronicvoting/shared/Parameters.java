package com.techgee.electronicvoting.shared;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Parameters {
	
	private Long id;
	private Long foreignKey;
	private String word;
	private UUID uuid;
	private Parameters parentParameters; 
	
	public Parameters(Parameters parameters) {
		this.id = parameters.id;
		this.foreignKey = parameters.foreignKey;
		this.word = parameters.word;
		this.uuid = parameters.uuid;
		this.parentParameters = parameters.getParentParameters();
	}

	
	/**
	 * @param id
	 * @param foreignKey
	 * @param word
	 * @param uuid
	 * @param parentParameters
	 */
	public Parameters(Long id, Long foreignKey, String word, UUID uuid, Parameters parentParameters) {
		super();
		this.id = id;
		this.foreignKey = foreignKey;
		this.word = word;
		this.uuid = uuid;
		this.parentParameters = parentParameters;
	}

	/**
	 * @param id
	 */
	public Parameters(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param foreignKey
	 */
	public Parameters(Long id, Long foreignKey) {
		super();
		this.id = id;
		this.foreignKey = foreignKey;
	}
	

	/**
	 * @param id
	 * @param foreignKey
	 * @param word
	 */
	public Parameters(Long id, Long foreignKey, String word) {
		super();
		this.id = id;
		this.foreignKey = foreignKey;
		this.word = word;
	}


	/**
	 * @param id
	 * @param word
	 */
	public Parameters(Long id, String word) {
		super();
		this.id = id;
		this.word = word;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the foreignKey
	 */
	public Long getForeignKey() {
		return foreignKey;
	}

	/**
	 * @param foreignKey the foreignKey to set
	 */
	public void setForeignKey(Long foreignKey) {
		this.foreignKey = foreignKey;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the parentParameters
	 */
	public Parameters getParentParameters() {
		return parentParameters;
	}

	/**
	 * @param parentParameters the parentParameters to set
	 */
	public void setParentParameters(Parameters parentParameters) {
		this.parentParameters = parentParameters;
	}
	
	

}
