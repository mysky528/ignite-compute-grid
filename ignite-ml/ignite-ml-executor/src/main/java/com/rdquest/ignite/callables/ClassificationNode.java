/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import org.apache.ignite.lang.IgniteCallable;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * ClassificationNode to be used to perform classification given a training
 * Classifier
 *
 * @author Corey
 */
public class ClassificationNode implements IgniteCallable<ClassificationResponse> {

    private final Classifier classifier;

    private final Instances dataset;

    /**
     * The constructor of the ClassificationNode
     *
     * @param dataset the dataset to be classified
     * @param classifier the training classifier to use for prediction
     */
    public ClassificationNode(Instances dataset, Classifier classifier) {
        this.classifier = classifier;
        this.dataset = dataset;
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public ClassificationResponse call() throws Exception {

        System.out.println("Performing Performance Evalutations");

        ClassificationResponse response = new ClassificationResponse();
        
       // classifier.

        //response.setPerformance(EvaluateDataset.testDataset(classifier, dataset));

        return response;

    }

}
