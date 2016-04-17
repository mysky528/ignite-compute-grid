/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.runner;

import com.rdquest.ignite.callables.ClassificationNode;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.tools.data.FileHandler;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 *
 * @author Corey
 */
public class ClassificationRunner {
    
    public static final String TRAINING_SET = "Training";

    public static void main(String[] args) {
        new ClassificationRunner().run();

    }

    public void run() {
        try (Ignite ignite = Ignition.start("C:\\config\\example-ignite.xml")) {

            CacheConfiguration<String, Dataset> cfg = new CacheConfiguration<>();
            cfg.setName("ClassificationData");
            cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            
            final IgniteCache<String, Dataset> cache = ignite.getOrCreateCache(cfg);
            
            File userSpecified;
            Scanner reader = new Scanner(System.in);

            System.out.println("Please provide the path to your dataset:");

            userSpecified = new File(reader.next());

            // Assumptions
            // Iris dataset size = 150
            // 100 learning data
            // 50 compare data
            ExecutorService exec = ignite.executorService();
            

            try {
                Dataset originalData = FileHandler.loadDataset(userSpecified, 4, ",");

                if (!originalData.isEmpty() && originalData.size() > 1) {
                    Dataset trainingData = new DefaultDataset(originalData.subList(0, 99));
                    cache.put(TRAINING_SET, trainingData);
                    Dataset classificationData1 = new DefaultDataset(originalData.subList(100, 109));
                    cache.put("1", classificationData1);
                    exec.submit(new ClassificationNode(trainingData, classificationData1));
                    Dataset classificationData2 = new DefaultDataset(originalData.subList(110, 119));
                    cache.put("2", classificationData2);
                    exec.submit(new ClassificationNode(trainingData, classificationData2));
                    Dataset classificationData3 = new DefaultDataset(originalData.subList(120, 129));
                    cache.put("3", classificationData3);
                    exec.submit(new ClassificationNode(trainingData, classificationData3));
                    Dataset classificationData4 = new DefaultDataset(originalData.subList(130, 139));
                    cache.put("4", classificationData4);
                    exec.submit(new ClassificationNode(trainingData, classificationData4));
                    Dataset classificationData5 = new DefaultDataset(originalData.subList(140, 149));
                    cache.put("5", classificationData5);
                    exec.submit(new ClassificationNode(trainingData, classificationData5));
                }
                
                //Collection<Integer> result = ignite.compute().apply(new ClassificationNode());
                
            } catch (IOException ex) {
                Logger.getLogger(ClassificationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }

          //  Collection<IgniteCallable<Double>> calls = new ArrayList<>();
            // calls.add(new DecisionComputer());

            // Execute collection of callables on the ignite.
            //  Double result = ignite.compute().call(new DecisionComputer());
//            int sum = res.stream().mapToInt(i -> i).sum();
            //   System.out.println(result);
//            System.out.println(">>> Total number of characters in the phrase is '" + sum + "'.");
//            System.out.println(">>> Check all nodes for output (this nod
//            Scanner reader = new Scanner(System.in);
//            System.out.println("Please Specify A CSV File: ");
//            String toCount = reader.next();
//            Collection<Integer> result = ignite.compute().apply(
//                    (String word) -> {
//                        System.out.println("Counting characters in word " + word + " ");
//                        return word.length();
//                    },
//                    Arrays.asList(toCount.split(" "))
//            );
//
        }
    }

    private void performClassification(Dataset dataset) {

    }

}
