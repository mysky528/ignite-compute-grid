/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import java.util.Map;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;


/**
 *
 * @author Corey
 */
public class ClassificationResponse {

    private Map<Object, PerformanceMeasure> performance;

    public Map<Object, PerformanceMeasure> getPerformance() {
        return performance;
    }

    public void setPerformance(Map<Object, PerformanceMeasure> performance) {
        this.performance = performance;
    }

}
