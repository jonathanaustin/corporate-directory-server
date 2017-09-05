package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 * Match an Event Type or Qualifier or Both.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Matcher extends Serializable {

	Qualifier getMatchQualifier();

	/**
	 * Check if the qualifier and matcher are a match.
	 *
	 * @param qualifier the event qualifier to test if it matches
	 * @return true if a match
	 */
	boolean matches(final Qualifier qualifier);

}
