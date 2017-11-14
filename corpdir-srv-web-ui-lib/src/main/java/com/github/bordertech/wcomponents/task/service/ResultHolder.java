package com.github.bordertech.wcomponents.task.service;

/**
 * Service result holder to use in the Future.
 * <p>
 * The result can be an exception or the result. Does not need to serializable as it is held on a Future that is not
 * Serializable.
 * </p>
 *
 * @author Jonathan Austin
 * @param <T> the result type
 * @since 1.0.0
 */
public class ResultHolder<T> {

	private T result;
	private Exception exception;

	public ResultHolder() {
	}

	public ResultHolder(final T result) {
		this.result = result;
	}

	public ResultHolder(final Exception exception) {
		this.exception = exception;
	}

	/**
	 * @return the polling result
	 */
	public T getResult() {
		return result;
	}

	/**
	 * @param result the polling result
	 */
	public void setResult(final T result) {
		this.result = result;
		this.exception = null;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(final Exception exception) {
		this.exception = exception;
		this.result = null;
	}

	public boolean hasException() {
		return exception != null;
	}

	public boolean hasResult() {
		return result != null;
	}

}
