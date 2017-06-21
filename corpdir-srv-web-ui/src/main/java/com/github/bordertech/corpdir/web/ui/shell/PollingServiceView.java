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
public interface PollingServiceView<S, T> extends BasicEventView {

	void setCriteria(final S criteria);

	S getCriteria();

	T getResult();

	void refreshView();

	void addViewAction(final PollingServiceEvent viewEvent, final ViewAction<PollingServiceView<S, T>, PollingServiceEvent> viewAction);

}
