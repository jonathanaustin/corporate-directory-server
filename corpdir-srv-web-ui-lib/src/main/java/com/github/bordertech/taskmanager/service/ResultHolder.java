package com.github.bordertech.taskmanager.service;

/**
 * Service result holder to use in the Future.
 * <p>
 * The result can be an exception or the result. Does not need to serializable as it is held on a Future that is not
 * Serializable.
 * </p>
 *
 * @author Jonathan Austin
 * @param <S> the criteria type
 * @param <T> the result type
 * @since 1.0.0
 */
public class ResultHolder<S, T> {

	private final S criteria;
	private T result;
	private Exception exception;

	public ResultHolder(final S criteria) {
		this.criteria = criteria;
	}

	public ResultHolder(final S criteria, final T result) {
		this(criteria);
		this.result = result;
	}

	public ResultHolder(final S criteria, final Exception exception) {
		this(criteria);
		this.exception = exception;
	}

	public S getCriteria() {
		return criteria;
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
