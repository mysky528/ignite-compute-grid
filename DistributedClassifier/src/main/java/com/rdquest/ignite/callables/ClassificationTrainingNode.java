/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.core.Dataset;
import org.apache.ignite.lang.IgniteCallable;

/**
 *
 * @author Corey
 */
public class ClassificationTrainingNode<T> implements IgniteCallable<T> {

    private final Dataset training;
    
    private final Integer neighborCount;

    public ClassificationTrainingNode(Dataset training, Integer neighborCount) {
        this.training = training;
        this.neighborCount = neighborCount;
    }

    @Override
    public T call() throws Exception {

        System.out.println("Computing KNN");

        Classifier knn = new KNearestNeighbors(neighborCount);
        knn.buildClassifier(training);

        return (T) knn;

    }

}
