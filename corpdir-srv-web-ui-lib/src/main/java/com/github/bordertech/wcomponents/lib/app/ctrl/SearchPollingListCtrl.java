package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.SearchEventType;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;

/**
 * Controller for a Polling View and List View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public class SearchPollingListCtrl<S, T> extends PollingListCtrl<S, T> {

	public SearchPollingListCtrl() {
		this(null);
	}

	public SearchPollingListCtrl(final String qualifier) {
		super(qualifier);
	}

	@Override
	public void setupListeners() {
		super.setupListeners();

		// SEARCH Listeners
		for (SearchEventType type : SearchEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleSearchEvents(event);
				}
			};
			registerListener(listener, type);
		}
	}

	public final CriteriaView<S> getSearchView() {
		return getComponentModel().searchView;
	}

	public final void setSearchView(final CriteriaView<S> searchView) {
		getOrCreateComponentModel().searchView = searchView;
		addView(searchView);
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getSearchView() == null) {
			throw new IllegalStateException("A search view has not been set.");
		}
	}

	protected void handleSearchEvents(final Event event) {
		SearchEventType type = (SearchEventType) event.getQualifier().getEventType();
		switch (type) {
			case SEARCH_VALIDATING:
				dispatchCtrlEvent(ListEventType.RESET_LIST);
				break;
			case SEARCH:
				dispatchCtrlEvent(ListEventType.START_SEARCH, event.getData());
				break;
		}
	}

	@Override
	protected SearchPollingListModel<S, T> newComponentModel() {
		return new SearchPollingListModel();
	}

	@Override
	protected SearchPollingListModel<S, T> getComponentModel() {
		return (SearchPollingListModel) super.getComponentModel();
	}

	@Override
	protected SearchPollingListModel<S, T> getOrCreateComponentModel() {
		return (SearchPollingListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class SearchPollingListModel<S, T> extends PollingListModel<S, T> {

		private CriteriaView<S> searchView;
	}
}
