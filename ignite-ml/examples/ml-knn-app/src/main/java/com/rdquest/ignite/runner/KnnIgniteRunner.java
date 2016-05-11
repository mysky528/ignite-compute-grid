/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.executor.IgniteExecutor;
import com.rdquest.ignite.ml.api.requests.IgniteKnnRequest;
import com.rdquest.ignite.ml.api.responses.IgniteKnnResponse;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 *
 * @author Corey
 */
public final class KnnIgniteRunner {

	public static void main(String[] args) {

		File trainingDataPath;
		File actualDataPath;

		Scanner reader = new Scanner(System.in);

		System.out.println("Please provide the path to your training dataset: ");

		trainingDataPath = new File(reader.next());

		System.out.println("Please provide the path to your actual (non-training) data: ");

		actualDataPath = new File(reader.next());


		IgniteKnnRequest request = new IgniteKnnRequest();
		try {
			CSVLoader trainingLoader = new CSVLoader();
			CSVLoader testLoader = new CSVLoader();
			trainingLoader.setSource(trainingDataPath);
			testLoader.setSource(actualDataPath);
			trainingLoader.setNoHeaderRowPresent(true);
			testLoader.setNoHeaderRowPresent(true);

			Instances trainingData = trainingLoader.getDataSet();
			Instances testData = testLoader.getDataSet();
			
			IgniteExecutor executor = IgniteExecutorFactory.createInstance(trainingData);

			// setting class attribute
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
			testData.setClassIndex(testData.numAttributes() - 1);

			request.setTestData(testData);
			request.setTrainingData(trainingData);

			IgniteKnnResponse response = executor.handleRequest(request);
			response.getClassifiedAggregate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
