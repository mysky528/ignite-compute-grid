package com.rdquest.ignite.runner;

import com.rdquest.ignite.ml.api.executor.IgniteExecutor;

public class IgniteExecutorFactory {

	public static IgniteExecutor createInstance() {

		return new IgniteMLExecutor();
	}

}
