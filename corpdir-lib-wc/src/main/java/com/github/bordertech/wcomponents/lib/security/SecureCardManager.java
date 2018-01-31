package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Secure Card Manager and Path.
 * <p>
 * Use {@link WLibServlet} to setup the {@link EnvironmentHelper}.
 * </p>
 *
 * @author Jonathan Austin
 * @param <T> the card type
 */
public interface SecureCardManager<T extends SecureCard> extends WComponent {

	void addSecureCard(final T card);

	void removeSecureCard(final T card);

	List<T> getSecureCards();

	T getSecureCard(final String path);

	void setSecureMode(final boolean secureMode);

	boolean isSecureMode();

	T getCurrentCard();

	void setCurrentCard(final T card);

	boolean isCurrentCard(final T card);

}
