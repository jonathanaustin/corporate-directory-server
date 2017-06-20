package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Polling View.
 *
 * @param <S> the criteria type
 * @param <T> the result type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingServiceView<S, T> extends BasicEventView<PollingServiceView<S, T>, PollingServiceEvent> {

	void setCriteria(final S criteria);

	S getCriteria();

	T getResult();

	void refreshView();
}
