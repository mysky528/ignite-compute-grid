/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.runner;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.executor.IgniteExecutor;
import com.rdquest.ignite.ml.api.executor.IgniteMLHandler;
import com.rdquest.ignite.ml.request.handlers.IgniteKnnHandler;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import com.rdquest.ignite.ml.api.requests.IgniteKnnRequest;
import com.rdquest.ignite.ml.api.requests.IgniteMLRequest;
import com.rdquest.ignite.ml.api.responses.IgniteMLResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Corey
 */
public final class IgniteMLExecutor implements IgniteExecutor {

	private Map<Class<? extends IgniteMLRequest>, IgniteMLHandler> handlerMap = new HashMap<>();

	private Ignite igniteInstance;

	public static final String TRAINING_SET = "Training";

	public IgniteMLExecutor(Instances trainingSet) throws IgniteMLException {
		IgniteConfiguration configuration = new IgniteConfiguration();
		
		configuration.setPeerClassLoadingEnabled(true);
		Ignite ignite = Ignition.start(configuration);
		this.setIgniteInstance(ignite);

		registerHandlers(trainingSet);
	}

	@Override
	public <T extends IgniteMLResponse> T handleRequest(IgniteMLRequest request) throws IgniteMLException {
		// Lookup the correct handler
		IgniteMLHandler<IgniteMLRequest, T> handler = handlerMap.get(request.getClass());
		if (handler == null) {
			throw new IgniteMLException("Unable to find handler for request class: " + request.getClass().getName());
		}
		return handler.run(request, igniteInstance.executorService(), igniteInstance.cluster().nodes().size());
	}

	/**
	 * Allows a caller to register new and custom request handlers.
	 * 
	 * @param clazz
	 * @param handler
	 * @param response
	 */
	public void registerHandler(Class<IgniteKnnRequest> clazz,
			IgniteMLHandler<IgniteMLRequest, IgniteMLResponse> handler) {
		handlerMap.put(clazz, handler);
	}

	/**
	 * Used to register the default set of handlers
	 * @throws IgniteMLException 
	 */
	private void registerHandlers(Instances trainingData) throws IgniteMLException {
		IgniteMLHandler handler = new IgniteKnnHandler();
		handler.trainHandler(trainingData);
		handlerMap.put(IgniteKnnRequest.class, handler);

	}

	/**
	 * Gets the instance of Ignite. Operations to the Ignite instance should be
	 * exercised with caution since they will affect running requests
	 * 
	 * @return
	 */
	public Ignite getIgniteInstance() {
		return igniteInstance;
	}

	/**
	 * Sets the Ignite instance. Only done internal to this class
	 * 
	 * @param igniteInstance
	 */
	private void setIgniteInstance(Ignite igniteInstance) {
		this.igniteInstance = igniteInstance;
	}
}
