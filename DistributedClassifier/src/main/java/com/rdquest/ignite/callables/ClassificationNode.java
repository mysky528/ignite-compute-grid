/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.core.Dataset;
import org.apache.ignite.lang.IgniteCallable;

/**
 *
 * @author Corey
 * @param <T>
 */
public class ClassificationNode<T> implements IgniteCallable<T> {

    private final Classifier classifier;
    
    private final Dataset dataset;

    /**
     *
     * @param dataset
     * @param classifier
     */
    public ClassificationNode(Dataset dataset, Classifier classifier) {
        this.classifier = classifier;
        this.dataset = dataset;
    }

    @Override
    public T call() throws Exception {

        System.out.println("Performing Performance Evalutations");

        ClassificationResponse response = new ClassificationResponse();

        response.setPerformance(EvaluateDataset.testDataset(classifier, dataset));

        return (T) response;

    }

}
