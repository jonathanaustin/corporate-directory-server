package com.github.bordertech.corpdir.web.ui.polling;

/**
 * Status of the Polling Controller and if the service has been called successfully.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingStatus {
	/**
	 * Service not called yet.
	 */
	NOT_STARTED, /**
	 * Service being called.
	 */
	PROCESSING, /**
	 * Service call had an error.
	 */
	ERROR, /**
	 * Service called successfully.
	 */
	COMPLETE

}
