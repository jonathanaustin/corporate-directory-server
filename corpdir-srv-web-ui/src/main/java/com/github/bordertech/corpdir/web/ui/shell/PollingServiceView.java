package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Entity Criteria View.
 *
 * @param <S> the criteria type
 * @param <T> the result type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingServiceView<S, T> extends BasicEventView {

	void addPollingAction(final PollingServiceEvent event, final PollingServiceAction<S, T> action);

	void setCriteria(final S criteria);

	S getCriteria();

	T getResult();

	void refreshView();
}
