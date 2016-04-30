/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.runner;

import com.rdquest.ignite.callables.ClassificationResponse;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import com.rdquest.ignite.ml.api.executor.AbstractIgniteExecutor;
import com.rdquest.ignite.ml.request.handlers.IgniteKnnHandler;
import com.rdquest.ignite.ml.api.requests.IgniteKnnRequest;
import com.rdquest.ignite.ml.api.requests.IgniteMLRequest;
import com.rdquest.ignite.ml.api.responses.IgniteMLResponse;
import com.rdquest.ignite.ml.request.handlers.IgniteMLHandler;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Corey
 */
public class IgniteMLExecutor extends AbstractIgniteExecutor {

    private Map<Class, IgniteMLHandler> handlerMap = new HashMap<>();

    public static final String TRAINING_SET = "Training";

    public static void main(String[] args) {
        new IgniteMLExecutor().run();

    }

    @Override
    public Ignite createInstance() {
        IgniteConfiguration configuration = new IgniteConfiguration();

        configuration.setPeerClassLoadingEnabled(true);

        return Ignition.start(configuration);
    }

    public void registerHandlers() {
        handlerMap.put(IgniteKnnRequest.class, new IgniteKnnHandler());

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

//            try {
//                // Get the training dataset for the KNN algorithm
//                // Classindex is the columnId of the class
//                // For now we only handle data that's split by commas
//                Dataset trainingData = FileHandler.loadDataset(trainingDataPath, classIndex, ",");
//
//                if (!trainingData.isEmpty() && trainingData.size() > 1) {
//
//                    // Future is used to get the response when the callable is complete
//                    List<Future<ClassificationResponse>> tasks = new ArrayList<>();
//
//                    // Start tracking run time
//                    //TODO Could eventually use better timing library
//                    Long startTime = System.nanoTime();
//
//                    Future<Classifier> futureResponse = exec.submit(
//                            new ClassificationTrainingNode(trainingData, 5));
//                    // The classifer from the training dataset
//                    // Wait up to ten minutes for completion. Really arbitrary, and shouldn't
//                    // take that long
//                    Classifier classifier = futureResponse.get(10, TimeUnit.MINUTES);
//
//                    // Crude dataset splits
//                    Dataset realData = FileHandler.loadDataset(actualDataPath, classIndex, ",");
//
//                    if (!realData.isEmpty() && realData.size() > 1) {
//
//                        List<Dataset> splitSets = new ArrayList<>();
//
//                        int remainder = (realData.size() % numberOfNodes);
//                        int sizeOfSplitSets = (realData.size() / numberOfNodes);
//                        int lastSet = (remainder + sizeOfSplitSets);
//                        if ((remainder) == 0) { // 
//                            for (int i = 0; i < realData.size(); i += sizeOfSplitSets) {
//                                splitSets.add(new DefaultDataset(realData.subList(i, i + sizeOfSplitSets)));
//                            }
//                        } else {
//                            for (int i = 0; i < (realData.size() - lastSet); i += sizeOfSplitSets) {
//                                splitSets.add(new DefaultDataset(realData.subList(i, i + sizeOfSplitSets)));
//                            }
//                            // Now add the last
//                            splitSets.add(
//                                    new DefaultDataset(realData.subList(
//                                            (realData.size() - lastSet), realData.size() - 1)));
//
//                        }
//                        // Utilization of Java 8 parallel stream for performance
//                        // Essentially launches the nodes in parallel
//                        splitSets.parallelStream().forEach((set) -> {
//                            tasks.add(exec.submit(
//                                    new ClassificationNode(set, classifier)));
//                        });
//                    }
//                    while (isExecuting(tasks)) {
//                        // Note: this is a crude way of doing this.
//                    }
//
//                    System.out.println("Execution Delta (Milliseconds): " + (System.nanoTime() - startTime) / 1000000);
//                    // Done Now
//                    // Print out the results
//                    int taskNumber = 1;
//                    for (Future<ClassificationResponse> task : tasks) {
//                        System.out.println("Performance for task: " + taskNumber++);
//                        for (Entry<Object, PerformanceMeasure> performance
//                                : task.get().getPerformance().entrySet()) {
//                            System.out.println("Performance: " + performance.getValue());
//                            System.out.println("Accuracy: " + performance.getValue().getAccuracy());
//
//                        }
//                    }
//
//                }
//
//            } catch (InterruptedException | ExecutionException | IOException | TimeoutException ex) {
//                String failureMessage = "An error occured while running classification";
//                Logger.getLogger(ClassificationRunner.class.getName()).log(Level.SEVERE, failureMessage, ex);
//            }
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

	@Override
	public IgniteMLResponse handleRequest(IgniteMLRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
