package com.github.bordertech.corpdir.web.ui.polling;

import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WPanel;

/**
 * This interface is used to load data via a threaded service call and polling AJAX.
 * <p>
 * Expected {@link #setRecordId(Object)} to be set with the appropriate id to be loaded.
 * </p>
 * <p>
 * The successful service response will be set as the bean available to the panel. The content of the panel will only be
 * displayed if the service call was successful. If the service call fails, then the error message will be displayed
 * along with a retry button.
 * </p>
 * <p>
 * Any caching of service calls is expected to be handled at the services layer.
 * </p>
 *
 * @param <T> the service response type
 * @param <R> the record id type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingController<T, R> extends WComponent {

	/**
	 * Reset to start load again.
	 */
	void doRefreshContent();

	/**
	 * Retry the service call.
	 */
	void doRetry();

	/**
	 * Start loading data.
	 */
	void doStartLoading();

	/**
	 * The container used to hold the panel detail.
	 *
	 * @return the panel content holder
	 */
	WPanel getContent();

	/**
	 * @return the panel status
	 */
	PollingStatus getPollingStatus();

	/**
	 * @return the AJAX polling interval in milli seconds
	 */
	int getPollingInterval();

	/**
	 * @return the text displayed while polling
	 */
	String getPollingText();

	/**
	 * @return the id for the record
	 */
	R getRecordId();

	/**
	 * @return the retry button.
	 */
	WButton getRetryButton();

	/**
	 * @return the start button
	 */
	WButton getStartButton();

	/**
	 * @return true if start polling manually with the start button.
	 */
	boolean isManualStart();

	/**
	 *
	 * @param manualStart true if start polling manually with the start button
	 */
	void setManualStart(final boolean manualStart);

	/**
	 *
	 * @param interval the AJAX polling interval in milli seconds
	 */
	void setPollingInterval(final int interval);

	/**
	 * @param text the text displayed while polling
	 */
	void setPollingText(final String text);

	/**
	 * @param recordId the id for the record
	 */
	void setRecordId(final R recordId);

	/**
	 * Do the actual service call.
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param recordId the id for the record
	 * @return the response
	 * @throws PollingServiceException a service exception occurred
	 */
	T doServiceCall(final R recordId) throws PollingServiceException;

	/**
	 * @return the service response, or null if not called successfully
	 */
	T getServiceResponse();

}
