/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import org.apache.ignite.lang.IgniteCallable;

/**
 * The classification training node. Receives a dataset to use for training and
 * returns a Classifier to be used for predicative classification
 *
 * @author Corey
 */
public class ClassificationTrainingNode implements IgniteCallable<Classifier> {

    private final Dataset training;

    private final Integer neighborCount;

    /**
     * Constructor of the ClassificationTrainingNode
     *
     * @param training the training dataset
     * @param neighborCount the number to be used by the KNN algorithm
     */
    public ClassificationTrainingNode(Dataset training, Integer neighborCount) {
        this.training = training;
        this.neighborCount = neighborCount;
    }

    /**
     * Call method that executes the KNN classification training
     *
     * @return @throws Exception
     */
    @Override
    public Classifier call() throws Exception {

        System.out.println("Computing KNN Training on");

        Classifier knn = new KNearestNeighbors(neighborCount);
        knn.buildClassifier(training);

        return knn;

    }

}
