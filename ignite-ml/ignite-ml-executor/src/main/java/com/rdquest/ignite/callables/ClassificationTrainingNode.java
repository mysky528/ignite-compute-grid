/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import org.apache.ignite.lang.IgniteCallable;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 * The classification training node. Receives a dataset to use for training and
 * returns a Classifier to be used for predicative classification
 *
 * @author Corey
 */
public class ClassificationTrainingNode implements IgniteCallable<Classifier> {

    private final Instances training;

    /**
     * Constructor of the ClassificationTrainingNode
     *
     * @param training the training dataset
     */
    public ClassificationTrainingNode(Instances training) {
        this.training = training;
    }

    /**
     * Call method that executes the KNN classification training
     *
     * @return @throws Exception
     */
    @Override
    public Classifier call() throws Exception {

        System.out.println("Computing KNN Training on");

        Classifier knn = new IBk();
        knn.buildClassifier(training);

        return knn;

    }

}
