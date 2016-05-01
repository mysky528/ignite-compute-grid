/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.api.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.ignite.lang.IgniteCallable;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.requests.IgniteMLRequest;
import com.rdquest.ignite.ml.api.responses.IgniteMLResponse;

import weka.core.Instances;

/**
 *
 * @author Corey
 * @param <R>
 */
public interface IgniteMLHandler<T extends IgniteMLRequest, T1 extends IgniteMLResponse> {

	public T1 run(T request, ExecutorService exec, Integer numNodes) throws IgniteMLException;

	public default List<Instances> scaleDataset(Instances dataset, Integer numNodes) {

		int numInstances = dataset.numInstances();

		if (dataset != null && numInstances > 1) {

			List<Instances> splitSets = new ArrayList<>();

			int remainder = (numInstances % numNodes);
			int sizeOfSplitSets = (numInstances / numNodes);
			int lastSet = (remainder + sizeOfSplitSets);
			if ((remainder) == 0) {
				for (int i = 0; i < numInstances; i += sizeOfSplitSets) {
					splitSets.add(new Instances(dataset, i, i + sizeOfSplitSets));
				}
			} else {
				for (int i = 0; i < (numInstances - lastSet); i += sizeOfSplitSets) {
					splitSets.add(new Instances(dataset, i, i + sizeOfSplitSets));
				}
				// Now add the last
				splitSets.add(new Instances(dataset, (numInstances - lastSet), (numInstances - 1)));
			}

		}
		return null;
	}

	public default List<Future<T1>> submitTasks(List<IgniteCallable> callables, ExecutorService exec) {
		List<Future<T1>> futures = new ArrayList<>();
		// Utilization of Java 8 parallel stream for performance
		// Essentially launches the nodes in parallel
		callables.parallelStream().forEach((callable) -> {
			futures.add(exec.submit(callable));
		});
		return futures;

	}

}
