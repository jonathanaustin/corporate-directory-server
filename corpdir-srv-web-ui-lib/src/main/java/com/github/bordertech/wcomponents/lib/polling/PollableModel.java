package com.github.bordertech.wcomponents.lib.polling;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface PollableModel<S, T> {

	T service(final S criteria);

}
