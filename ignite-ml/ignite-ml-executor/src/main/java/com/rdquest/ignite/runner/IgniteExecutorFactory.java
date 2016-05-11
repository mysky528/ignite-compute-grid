package com.rdquest.ignite.runner;

import com.rdquest.ignite.ml.api.executor.IgniteExecutor;

import weka.core.Instances;

public class IgniteExecutorFactory {

	public static IgniteExecutor createInstance(Instances trainingSet) throws Exception {

		return new IgniteMLExecutor(trainingSet);
	}

}
