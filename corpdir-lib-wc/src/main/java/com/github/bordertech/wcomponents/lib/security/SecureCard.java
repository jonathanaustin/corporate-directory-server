package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;

/**
 *
 * @author jonathan
 */
public interface SecureCard extends WComponent {

	AppPath getAppPath();

	/**
	 * Called when the Card is navigated to.
	 *
	 * @param request the request being processed
	 */
	void handleShowCardRequest(final Request request);

	/**
	 * Check if the card has not changed but the parameters have.
	 *
	 * @param request the request being processed
	 */
	void handleCheckCardRequest(final Request request);

}
