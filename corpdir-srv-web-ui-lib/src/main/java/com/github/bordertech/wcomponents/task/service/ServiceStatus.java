package com.github.bordertech.wcomponents.task.service;

/**
 * Status of the service action and if the action has been processed successfully.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ServiceStatus {
	/**
	 * Not started yet.
	 */
	NOT_STARTED,
	/**
	 * Service action started.
	 */
	STARTED,
	/**
	 * Error occurred.
	 */
	ERROR,
	/**
	 * Task action completed successfully.
	 */
	COMPLETE

}
