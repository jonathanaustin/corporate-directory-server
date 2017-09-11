package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface Pollable extends WComponent {

	/**
	 *
	 * @return the extra AJAX targets
	 */
	List<AjaxTarget> getAjaxTargets();

	/**
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
	 * @return the panel status
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
	 * @param panelStatus the panel status
	 */
	void setPollingStatus(final PollingStatus panelStatus);

	/**
	 * @param text the text displayed while polling
	 */
	void setPollingText(final String text);

}
