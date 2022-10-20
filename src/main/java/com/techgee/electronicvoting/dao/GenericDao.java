package com.techgee.electronicvoting.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.techgee.electronicvoting.exception.VotingException;

public interface GenericDao <T, P, S>{
	
	 /** The new instance of model object into database */
	default T create(@NotNull T model, P parameters) {
    	return createV1(Objects.requireNonNull(model, "Model can't be null"), parameters).orElse(null);
    }

	/** The new instance of model object into database */
	Optional<T> createV1(@NotNull T requireNonNull, P parameters);
	
	/** The new instance of model object into database*/
	Long insert(T model, P parameters);
	
	/**
     * Retrieve an object that was previously added to the database using
     * the indicated parameter and where clause else insert new row and retrieve
	 * @throws Exception 
     */
	default T upsert(T model, P parameters) {
		return getV1(parameters).orElseGet(()->create(model, parameters));
	}
	
	/** Retrieve an object that was previously added to the database using
     *  the indicated parameters 
	 * @throws Exception 
     */
	default T get(@NotNull P parameters)  {
    	return getV1(Objects.requireNonNull(parameters, "Parameters in the get method should not be null")).orElse(null);
    }

	/** Retrieve an Optional<object> that was previously added to the database using
     *  the indicated parameter
	 * @throws Exception 
     */
	Optional<T> getV1(P parameters);
	
	default T get(P parameters, S whereClause) {
    	return getV1(parameters, whereClause).orElse(null);
    }
    
    /** Retrieve an Optional<object> that was previously added to the database using
     *  the indicated parameters  and whereClause
     * @throws Exception 
     */
    Optional<T> getV1(P parameters, S whereClause);
   
    /**
     * Retrieve all instance using parameters
     * @throws Exception 
     */
    List<T> list(P parameters);
    
    /**
     * Retrieve all instance using the indicated parameters and where clause
     * @throws Exception 
     */
    List<T> list(P parameters, S whereClause);
    
     
    /** Save changes made to a persistent object. */
    default T update(T transientObject, P parameters) {
    	return updateV1(transientObject, parameters).orElse(null);
    }
    
    /** Save changes made to a persistent object. */
    Optional<T> updateV1(T transientObject, P parameters);
    
    /** Remove an object from persistent storage in the database */
    int delete(T persistentObject);

    /** Remove an object from persistent storage in the database 
     * @throws Exception */
    default int deleteV1(Optional<T> persistentObject) {
    	return delete(persistentObject.orElseThrow(()->new VotingException("The requested object is not found in database")));
    }
    
    /** Remove all objects from persistent storage in the database
     * using parameters and where clause (mostly Foreign key)
     * @throws Exception */
    int delete(P parameters, S whereClause);
    
    default int findAndDelete(P parameters) {
    	return deleteV1(getV1(parameters));
    }
	
 
	/**
	 * Replace the string with in-values with default delimiter "," or the requested delimiter
	 * @param <E extends Number>
	 * @param query
	 * @param replaceString
	 * @param inValues
	 * @param delimitter
	 * @return query with inValues
	 */
	default <E extends Number> String setInvalues(String query, String replaceString, Set<E> inValues, String... delimitter) {
		return query.replace(replaceString, inValues.stream().map(Object::toString)
				.collect(Collectors.joining(delimitter.length > 0 ? delimitter[0]:",")));
	}
	
	default String setInvalues(String query, String replaceString, Set<String> inValues) throws Exception {
		if (inValues == null || inValues.isEmpty()) {
			throw new Exception(" List cannot be empty");
		}
		return query.replace(replaceString, "'" + inValues.stream().map(Object::toString).collect(Collectors.joining("','")) + "'");
	}
	
	
	/**
	 * Replace the STRING between 2 String (EXCLUSIVE)
	 * @param originalString
	 * @param startWord
	 * @param endWord
	 * @param replaceString
	 * @return STRING WITH REPLACED STRING
	 */
	static String replaceBetween2Words(String originalString, String startWord, String endWord, String replaceString) {
		return originalString.replaceAll("("+startWord + ")[^&]*("+endWord+")", "$1"+replaceString+"$2");
	}
	

}
