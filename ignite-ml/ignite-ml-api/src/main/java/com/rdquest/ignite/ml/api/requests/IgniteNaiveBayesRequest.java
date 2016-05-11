package com.rdquest.ignite.ml.api.requests;

import weka.core.Instances;

public class IgniteNaiveBayesRequest extends IgniteMLRequest {
	
    private Instances testData;

    public Instances getTestData() {
        return testData;
    }

    public void setTestData(Instances testData) {
        this.testData = testData;
    }

}
