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

    private Instances testData;

    public Instances getTestData() {
        return testData;
    }

    public void setTestData(Instances testData) {
        this.testData = testData;
    }

}
