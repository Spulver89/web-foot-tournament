package com.wft.service.business;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wft.service.business.exception.ServiceSecurityException;

/**
 * {@link AsyncCallback} switch recognises {@link ServiceSecurityException} and
 * handles it gracefully.
 * 
 */
public abstract class AutoErrorHandlingAsyncCallback<T> implements AsyncCallback<T> {

	final public void onFailure(Throwable throwable) {
		if (throwable instanceof ServiceSecurityException) {
			onSecurityException (throwable);
		} else {
		    onException (throwable);
		}
		
	}

    /**
     * Method to override if user want to react on exception
     * @param throwable
     */
    protected abstract void onException(Throwable throwable);

    /**
     * Method to override if user want to react on exception related to security
     * @param throwable
     */
    protected abstract void onSecurityException(Throwable throwable);
}