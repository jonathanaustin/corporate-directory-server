package com.github.bordertech.taskmaster;

/**
 * Task Manager Exception has occurred.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TaskManagerException extends RuntimeException {

	/**
	 * Creates a TaskManagerException with the specified message.
	 *
	 * @param msg the message.
	 */
	public TaskManagerException(final String msg) {
		super(msg);
	}

	/**
	 * Creates a TaskManagerException with the specified message and cause.
	 *
	 * @param msg the message.
	 * @param throwable the cause of the exception.
	 */
	public TaskManagerException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * Creates a TaskManagerException with the specified cause.
	 *
	 * @param throwable the cause of the exception.
	 */
	public TaskManagerException(final Throwable throwable) {
		super(throwable);
	}
}
