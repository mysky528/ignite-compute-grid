/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.callables;

import net.sf.javaml.core.Dataset;

/**
 *
 * @author Corey
 */
public class ClassificationDatasets {
    
    private Dataset originalData;
    
    private Dataset classificationData;

    public Dataset getOriginalData() {
        return originalData;
    }

    public void setOriginalData(Dataset originalData) {
        this.originalData = originalData;
    }

    public Dataset getClassificationData() {
        return classificationData;
    }

    public void setClassificationData(Dataset classificationData) {
        this.classificationData = classificationData;
    }
    
    
    
}
