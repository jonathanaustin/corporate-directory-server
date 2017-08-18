/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.model.RequiresServiceModel;

/**
 *
 * @author jonathan
 */
public interface PollingService<S, T> extends Polling, RequiresServiceModel<S, T> {

	/**
	 * Manually set the criteria and the result.
	 *
	 * @param criteria the criteria
	 * @param result the result
	 */
	void doManuallyLoadResult(final S criteria, final T result);

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
	 * @return the id for the record
	 */
	S getPollingCriteria();

	/**
	 * @return the polling result, or null if not processed successfully yet
	 */
	T getPollingResult();

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
	 * @param criteria the id for the record
	 */
	void setPollingCriteria(final S criteria);

}
