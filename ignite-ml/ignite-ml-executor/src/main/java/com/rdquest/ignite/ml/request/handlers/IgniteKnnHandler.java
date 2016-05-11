/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.request.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.ignite.lang.IgniteCallable;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.executor.IgniteMLHandler;
import com.rdquest.ignite.ml.api.requests.IgniteKnnRequest;
import com.rdquest.ignite.ml.api.responses.IgniteKnnResponse;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 *
 * @author Corey
 */
public class IgniteKnnHandler implements IgniteMLHandler<IgniteKnnRequest, IgniteKnnResponse> {
	
	private static final Logger log = Logger.getLogger(IgniteKnnHandler.class.getName());
	
	private Classifier classifier;	

	@Override
	public IgniteKnnResponse run(IgniteKnnRequest request, ExecutorService exec, Integer numNodes)
			throws IgniteMLException {

		IgniteKnnResponse aggregateResponse = new IgniteKnnResponse();

		try {

			// Split the dataset
			List<Instances> instances = scaleDataset(request.getTestData(), numNodes);
			List<IgniteCallable> callables = new ArrayList<>();
			IgniteKnnResponse response = new IgniteKnnResponse();

			for (Instances insts : instances) {
				callables.add(new IgniteCallable<IgniteKnnResponse>() {

					@Override
					public IgniteKnnResponse call() throws Exception {
						for (int i = 0; i < insts.numInstances(); i++) {
							response.getClassified().put(i, getClassifier().classifyInstance(insts.instance(i)));
						}

						return response;
					}

				});
			}

			List<Future<IgniteKnnResponse>> tasks = submitTasks(callables, exec);
			tasks.parallelStream().forEach((task) -> {
				try {
					int nodeNumber = 0;
					aggregateResponse.getClassifiedAggregate().put("Node" + nodeNumber++,
							(task.get(10, TimeUnit.MINUTES).getClassified()));
				} catch (Exception e) {
					log.severe("Unable to retrieve response from Node: " + 1);
				}
			});

		} catch (Exception e) {
			throw new IgniteMLException("Unable to perform KNN Classification", e);
		}

		return aggregateResponse;
	}

	@Override
	public void trainHandler(Instances trainingData) throws IgniteMLException {
		Classifier ibk = new IBk();
		try {
			ibk.buildClassifier(trainingData);
		} catch (Exception e) {
			throw new IgniteMLException("Unable to train classifier", e);
		}
		this.setClassifier(ibk);
	}
	
	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

}
