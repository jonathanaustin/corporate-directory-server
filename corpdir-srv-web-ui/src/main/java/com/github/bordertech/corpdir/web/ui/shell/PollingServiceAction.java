package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Polling Service Action.
 *
 * @author jonathan
 * @param <S> the criteria data type
 * @param <T> the result data type
 */
public interface PollingServiceAction<S, T> extends ViewAction<PollingServiceView<S, T>, PollingServiceEvent> {

}
