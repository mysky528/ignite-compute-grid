package com.rdquest.ignite.ml.request.handlers;

import java.util.concurrent.ExecutorService;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.executor.IgniteMLHandler;
import com.rdquest.ignite.ml.api.requests.IgniteKnnRequest;
import com.rdquest.ignite.ml.api.requests.IgniteNaiveBayesRequest;
import com.rdquest.ignite.ml.api.responses.IgniteKnnResponse;
import com.rdquest.ignite.ml.api.responses.IgniteNaiveBayesResponse;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;

public class IgniteNaiveBayesHandler implements IgniteMLHandler<IgniteNaiveBayesRequest, IgniteNaiveBayesResponse> {

	@Override
	public IgniteNaiveBayesResponse run(IgniteNaiveBayesRequest request, ExecutorService exec, Integer numNodes)
			throws IgniteMLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void trainHandler(Instances trainingData) throws IgniteMLException {
		NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
		try {
			nb.buildClassifier(trainingData);
		} catch (Exception e) {
			throw new IgniteMLException("Unable to train classifier", e);
		}
	}

}
