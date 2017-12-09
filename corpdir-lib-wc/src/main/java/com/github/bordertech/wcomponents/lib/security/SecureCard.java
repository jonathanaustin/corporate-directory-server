package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;

/**
 *
 * @author jonathan
 */
public interface SecureCard extends WComponent {

	AppPath getAppPath();

	void handleCardRequest(final Request request);

}
