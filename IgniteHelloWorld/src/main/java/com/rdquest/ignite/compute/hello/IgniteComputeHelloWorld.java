/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.compute.hello;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 *
 * @author Corey
 */
public class IgniteComputeHelloWorld {

    public static void main(String[] args) {

        IgniteConfiguration configuration = new IgniteConfiguration();

        configuration.setPeerClassLoadingEnabled(true);
        try (Ignite ignite = Ignition.start(configuration)) {
            ClusterGroup rmts = ignite.cluster().forRemotes();

            // All nodes
            ignite.compute().broadcast(() -> System.out.println("Hello World!"));
            // Only remote nodes
            //ignite.compute(rmts).broadcast(()->System.out.println("Hello World!"));
        }
    }

}
