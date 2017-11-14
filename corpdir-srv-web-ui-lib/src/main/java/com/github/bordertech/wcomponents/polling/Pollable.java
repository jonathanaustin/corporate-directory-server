package com.github.bordertech.wcomponents.polling;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;
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
	void doReload();

	/**
	 * Start AJAX polling.
	 */
	void doStartPolling();

	/**
	 * @return the AJAX polling interval in milli seconds
	 */
	int getPollingInterval();

	/**
	 * @return the service status
	 */
	ServiceStatus getServiceStatus();

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
	 * @param serviceStatus the panel status
	 */
	void setServiceStatus(final ServiceStatus serviceStatus);

	/**
	 * @param text the text displayed while polling
	 */
	void setPollingText(final String text);

}
