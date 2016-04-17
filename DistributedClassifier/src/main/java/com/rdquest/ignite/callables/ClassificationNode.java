/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import com.rdquest.ignite.runner.ClassificationRunner;
import java.util.List;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteCallable;

/**
 *
 * @author Corey
 */
public class ClassificationNode implements IgniteCallable<ClassificationResponse> {

//    private final IgniteCache<String, List> cache;
//    private final String cacheKey;
    private final Dataset training;

    private final Dataset classification;

    public ClassificationNode(Dataset training, Dataset classification) {
//        this.cache = cache;
//        this.cacheKey = cacheKey;
        this.classification = classification;
        this.training = training;
    }

    @Override
    public ClassificationResponse call() throws Exception {

        Classifier knn = new KNearestNeighbors(5);
        knn.buildClassifier(training);

        ClassificationResponse response = new ClassificationResponse();

        response.setPerformance(EvaluateDataset.testDataset(knn, classification));

        return response;

    }

}
