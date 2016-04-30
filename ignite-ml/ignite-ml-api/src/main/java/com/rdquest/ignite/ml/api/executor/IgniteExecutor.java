/*
    Available for use publically
    Check out my blog: rdquest.com
 */
package com.rdquest.ignite.ml.api.executor;

import com.rdquest.ignite.ml.api.requests.IgniteMLRequest;
import com.rdquest.ignite.ml.api.responses.IgniteMLResponse;
import org.apache.ignite.Ignite;

/**
 *
 * @author Corey
 */
public interface IgniteExecutor {

    /**
     *
     * @return
     */
    public Ignite createInstance();

    /**
     *
     * @param request
     * @return
     */
    public IgniteMLResponse handleRequest(IgniteMLRequest request);

}
