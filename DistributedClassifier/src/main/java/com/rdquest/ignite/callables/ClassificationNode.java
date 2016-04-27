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
 * ClassificationNode to be used to perform classification given a training
 * Classifier
 *
 * @author Corey
 */
public class ClassificationNode implements IgniteCallable<ClassificationResponse> {

    private final Classifier classifier;

    private final Dataset dataset;

    /**
     * The constructor of the ClassificationNode
     *
     * @param dataset the dataset to be classified
     * @param classifier the training classifier to use for prediction
     */
    public ClassificationNode(Dataset dataset, Classifier classifier) {
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

        response.setPerformance(EvaluateDataset.testDataset(classifier, dataset));

        return response;

    }

}
