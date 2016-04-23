/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.compute.count;

import java.util.Arrays;
import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 *
 * @author Corey
 */
public class IgniteComputeWordCount {

    public static void main(String[] args) {
        new IgniteComputeWordCount().run();

    }

    public void run() {
        IgniteConfiguration configuration = new IgniteConfiguration();

        configuration.setPeerClassLoadingEnabled(true);

        try (Ignite ignite = Ignition.start(configuration)) {

            System.out.println(">>> Counting letters in the sentence: ");
            System.out.println("Count characters using closure");

            // Execute closure on all cluster nodes.
            Collection<Integer> res = ignite.compute().apply(
                    (String word) -> {
                        System.out.println();
                        System.out.println(">>> Printing '" + word + "' on this node from ignite job.");

                        // Return number of letters in the word.
                        return word.length();
                    },
                    // Job parameters. Ignite will create as many jobs as there are parameters.
                    Arrays.asList("Count characters using closure".split(" "))
            );

            int sum = res.stream().mapToInt(i -> i).sum();

            System.out.println();
            System.out.println(">>> Total number of characters in the phrase is '" + sum + "'.");
        }
    }

}
