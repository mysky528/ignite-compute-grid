/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.runner;

import com.rdquest.ignite.callables.ClassificationNode;
import com.rdquest.ignite.callables.ClassificationResponse;
import com.rdquest.ignite.callables.ClassificationTrainingNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.tools.data.FileHandler;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 *
 * @author Corey
 */
public class ClassificationRunner {

    public static final String TRAINING_SET = "Training";

    public static void main(String[] args) {
        new ClassificationRunner().run();

    }

    /**
     *
     */
    public void run() {
        IgniteConfiguration configuration = new IgniteConfiguration();

        configuration.setPeerClassLoadingEnabled(true);

        try (Ignite ignite = Ignition.start(configuration)) {

            File trainingDataPath;
            File actualDataPath;
            Integer numberOfNodes;
            Integer classIndex;

            Scanner reader = new Scanner(System.in);

            System.out.println("Please provide the path to your training dataset: ");

            trainingDataPath = new File(reader.next());

            System.out.println("Please provide the path to your actual (non-training) data: ");

            actualDataPath = new File(reader.next());

            System.out.println("Please indicate the index of the class: ");
            classIndex = reader.nextInt();

            System.out.println("Please indicate the number of nodes available: ");
            numberOfNodes = reader.nextInt();

            /* The Apache Ignite executor service to be used to execute IgniteCallable
            * classes.
             */
            ExecutorService exec = ignite.executorService();

            try {
                // Get the training dataset for the KNN algorithm
                // Classindex is the columnId of the class
                // For now we only handle data that's split by commas
                Dataset trainingData = FileHandler.loadDataset(trainingDataPath, classIndex, ",");

                if (!trainingData.isEmpty() && trainingData.size() > 1) {

                    // Future is used to get the response when the callable is complete
                    List<Future<ClassificationResponse>> tasks = new ArrayList<>();

                    // Start tracking run time
                    //TODO Could eventually use better timing library
                    Long startTime = System.nanoTime();

                    Future<Classifier> futureResponse = exec.submit(
                            new ClassificationTrainingNode(trainingData, 5));
                    // The classifer from the training dataset
                    // Wait up to ten minutes for completion. Really arbitrary, and shouldn't
                    // take that long
                    Classifier classifier = futureResponse.get(10, TimeUnit.MINUTES);

                    // Crude dataset splits
                    Dataset realData = FileHandler.loadDataset(actualDataPath, classIndex, ",");

                    if (!realData.isEmpty() && realData.size() > 1) {

                        List<Dataset> splitSets = new ArrayList<>();

                        int remainder = (realData.size() % numberOfNodes);
                        int sizeOfSplitSets = (realData.size() / numberOfNodes);
                        int lastSet = (remainder + sizeOfSplitSets);
                        if ((remainder) == 0) { // 
                            for (int i = 0; i < realData.size(); i += sizeOfSplitSets) {
                                splitSets.add(new DefaultDataset(realData.subList(i, i + sizeOfSplitSets)));
                            }
                        } else {
                            for (int i = 0; i < (realData.size() - lastSet); i += sizeOfSplitSets) {
                                splitSets.add(new DefaultDataset(realData.subList(i, i + sizeOfSplitSets)));
                            }
                            // Now add the last
                            splitSets.add(
                                    new DefaultDataset(realData.subList(
                                            (realData.size() - lastSet), realData.size() - 1)));

                        }
                        // Utilization of Java 8 parallel stream for performance
                        // Essentially launches the nodes in parallel
                        splitSets.parallelStream().forEach((set) -> {
                            tasks.add(exec.submit(
                                    new ClassificationNode(set, classifier)));
                        });
                    }
                    while (isExecuting(tasks)) {
                        // Note: this is a crude way of doing this.
                    }

                    System.out.println("Execution Delta (Milliseconds): " + (System.nanoTime() - startTime) / 1000000);
                    // Done Now
                    // Print out the results
                    int taskNumber = 1;
                    for (Future<ClassificationResponse> task : tasks) {
                        System.out.println("Performance for task: " + taskNumber++);
                        for (Entry<Object, PerformanceMeasure> performance
                                : task.get().getPerformance().entrySet()) {
                            System.out.println("Performance: " + performance.getValue());
                            System.out.println("Accuracy: " + performance.getValue().getAccuracy());

                        }
                    }

                }

            } catch (InterruptedException | ExecutionException | IOException | TimeoutException ex) {
                String failureMessage = "An error occured while running classification";
                Logger.getLogger(ClassificationRunner.class.getName()).log(Level.SEVERE, failureMessage, ex);
            }
        }
    }

    /**
     * Check to see if the tasks are still executing
     *
     * @param tasks the tasks running on the classification nodes
     * @return boolean to determine if still executing
     */
    private boolean isExecuting(List<Future<ClassificationResponse>> tasks) {
        boolean atLeastOneRunning = false;
        for (Future task : tasks) {
            if (!task.isDone()) {
                atLeastOneRunning = true;
            }
        }
        return atLeastOneRunning;
    }

    /**
     * Prints the output from the Classifier
     *
     * @param classifier the classifier to be printed
     */
    private void printClassifer(Classifier classifier) {

    }

}
