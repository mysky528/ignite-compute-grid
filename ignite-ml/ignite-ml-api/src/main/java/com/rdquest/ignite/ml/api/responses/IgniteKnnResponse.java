/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.api.responses;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Corey
 */
public class IgniteKnnResponse implements IgniteMLResponse {

	private Map<String, Map<Integer, Double>> classifiedAggregate = new HashMap<>();
	
	private Map<Integer, Double> classified = new HashMap<>();

	public Map<Integer, Double> getClassified() {
		return classified;
	}

	public void setClassified(Map<Integer, Double> classified) {
		this.classified = classified;
	}

	public Map<String, Map<Integer, Double>> getClassifiedAggregate() {
		return classifiedAggregate;
	}

	public void setClassifiedAggregate(Map<String, Map<Integer, Double>> classifiedAggregate) {
		this.classifiedAggregate = classifiedAggregate;
	}
	
	



}
