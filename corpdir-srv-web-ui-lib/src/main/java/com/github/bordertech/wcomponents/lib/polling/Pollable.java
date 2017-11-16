package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Component capable of polling.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Pollable extends WComponent {

	/**
	 * The AJAX targets that will be refreshed when the polling is complete.
	 *
	 * @return the extra AJAX targets
	 */
	List<AjaxTarget> getAjaxTargets();

	/**
	 * Add an AJAX target to be refreshed when the polling is complete.
	 *
	 * @param target the extra AJAX target to add
	 */
	void addAjaxTarget(final AjaxTarget target);

	/**
	 * Do AJAX Reload.
	 */
	void doPanelReload();

	/**
	 * Start AJAX polling.
	 */
	void doStartPolling();

	/**
	 * Retry the polling action.
	 */
	void doRetry();

	/**
	 * Reset to start polling again.
	 */
	void doRefreshContent();

	/**
	 * @return the AJAX polling interval in milli seconds
	 */
	int getPollingInterval();

	/**
	 * @return the service status
	 */
	PollingStatus getPollingStatus();

	/**
	 * @return the text displayed while polling
	 */
	String getPollingText();

	/**
	 *
	 * @param interval the AJAX polling interval in milli seconds
	 */
	void setPollingInterval(final int interval);

	/**
	 * @param pollingStatus the panel status
	 */
	void setPollingStatus(final PollingStatus pollingStatus);

	/**
	 * @param text the text displayed while polling
	 */
	void setPollingText(final String text);

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
