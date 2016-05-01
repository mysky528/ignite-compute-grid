/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.api.executor;

import com.rdquest.ignite.ml.api.exceptions.IgniteMLException;
import com.rdquest.ignite.ml.api.requests.IgniteMLRequest;
import com.rdquest.ignite.ml.api.responses.IgniteMLResponse;

/**
 *
 * @author Corey
 */
public interface IgniteExecutor {

	/**
	 *
	 * @param <T>
	 * @param request
	 * @return
	 */
	public <T extends IgniteMLResponse> T handleRequest(T response, IgniteMLRequest request) throws IgniteMLException;

}
