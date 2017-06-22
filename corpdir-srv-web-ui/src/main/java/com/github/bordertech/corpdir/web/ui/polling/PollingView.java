package com.github.bordertech.corpdir.web.ui.polling;

import com.github.bordertech.corpdir.web.ui.shell.BasicEventView;
import com.github.bordertech.corpdir.web.ui.shell.ViewAction;
import com.github.bordertech.wcomponents.WButton;

/**
 * Polling View.
 *
 * @param <S> the polling criteria type
 * @param <T> the polling result type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<S, T> extends BasicEventView {

	/**
	 * Register an event action.
	 *
	 * @param viewEvent the view event
	 * @param viewAction the view action
	 */
	void registerViewAction(final PollingEvent viewEvent, final ViewAction<PollingView<S, T>, PollingEvent> viewAction);

	/**
	 * @return the criteria for polling (eg Request or ID for a service call)
	 */
	S getPollingCriteria();

	/**
	 * @param criteria the criteria for polling
	 */
	void setPollingCriteria(final S criteria);

	/**
	 * Do the actual polling action (eg Service call).
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param criteria the criteria for the polling action
	 * @return the polling result
	 * @throws PollingException a service exception occurred
	 */
	T doPollingAction(final S criteria) throws PollingException;

	/**
	 * @return the polling result, or null if not called successfully
	 */
	T getPollingResult();

	/**
	 * Reset to start load again.
	 */
	void doRefreshContent();

	/**
	 * Retry the polling action.
	 */
	void doRetry();

	/**
	 * Start loading data.
	 */
	void doStartLoading();

	/**
	 * Manually load the result.
	 *
	 * @param criteria the polling criteria
	 * @param result the polling result
	 */
	void doManuallyLoadResult(final S criteria, final T result);

	/**
	 * @return the panel status
	 */
	PollingStatus getPollingStatus();

	/**
	 * @return the AJAX polling interval in milli seconds
	 */
	int getPollingInterval();

	/**
	 * @param interval the polling interval in milli seconds
	 */
	void setPollingInterval(final int interval);

	/**
	 * @return the text displayed while polling
	 */
	String getPollingText();

	/**
	 * @param pollingText the text displayed while polling
	 */
	void setPollingText(final String pollingText);

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

}
