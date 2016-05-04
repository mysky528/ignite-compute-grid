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

/**
 *
 * @author Corey
 */
public final class KnnIgniteRunner {

	public static void main(String[] args) {

		IgniteExecutor executor = IgniteExecutorFactory.createInstance();

		File trainingDataPath;
		File actualDataPath;
		Integer classIndex;

		Scanner reader = new Scanner(System.in);

		System.out.println("Please provide the path to your training dataset: ");

		trainingDataPath = new File(reader.next());

		System.out.println("Please provide the path to your actual (non-training) data: ");

		actualDataPath = new File(reader.next());

		System.out.println("Please indicate the index of the class: ");
		classIndex = reader.nextInt();

		IgniteKnnRequest request = new IgniteKnnRequest();
		BufferedReader trainingDataReader = null;
		BufferedReader testDataReader = null;
		try {
			trainingDataReader = new BufferedReader(new FileReader(trainingDataPath));
			testDataReader = new BufferedReader(new FileReader(trainingDataPath));
			
			Instances trainingData = new Instances(trainingDataReader);
			Instances testData = new Instances(testDataReader);
			
			trainingDataReader.close();
			testDataReader.close();
			// setting class attribute
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
			testData.setClassIndex(testData.numAttributes() - 1);

			 request.setTestData(testData);
			 request.setTrainingData(trainingData);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			IgniteKnnResponse response = executor.handleRequest(request);
		} catch (IgniteMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
