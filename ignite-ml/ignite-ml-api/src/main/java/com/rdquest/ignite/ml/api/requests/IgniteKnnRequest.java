/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.api.requests;

import weka.core.Instances;

/**
 *
 * @author Corey
 */
public class IgniteKnnRequest extends IgniteMLRequest {

    private Instances trainingData;

    private Instances testData;

    public Instances getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(Instances trainingData) {
        this.trainingData = trainingData;
    }

    public Instances getTestData() {
        return testData;
    }

    public void setTestData(Instances testData) {
        this.testData = testData;
    }

}
