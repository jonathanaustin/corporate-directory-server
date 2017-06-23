package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListEvent;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.polling.PollingEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingView;
import com.github.bordertech.wcomponents.lib.view.DefaultBasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;
import com.github.bordertech.wcomponents.lib.view.WDiv;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class AbstractSearchListController<S, T> extends DefaultBasicEventView implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final CriteriaView<S> criteriaView;
	private final PollingView<S, List<T>> pollingView;
	private final ListView<T> listView;

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return !pollingView.isVisible() && !listView.isVisible();
		}
	};

	public AbstractSearchListController(final CriteriaView<S> criteriaView, final PollingView<S, List<T>> pollingView, final ListView<T> listView) {

		WDiv holder = getViewHolder();

		this.criteriaView = criteriaView;
		this.pollingView = pollingView;
		this.listView = listView;

		// Actions
		// Criteria action
		criteriaView.registerViewAction(new ViewAction<CriteriaView<S>, CriteriaEvent>() {
			@Override
			public void execute(final CriteriaView<S> view, final CriteriaEvent viewEvent) {
				handleCriteria(view.getCriteria());
			}
		}, CriteriaEvent.SEARCH);

		// Polling action
		pollingView.registerViewAction(new ViewAction<PollingView<S, List<T>>, PollingEvent>() {
			@Override
			public void execute(final PollingView<S, List<T>> view, final PollingEvent viewEvent) {
				handleSearchResult(view.getPollingResult());
			}
		}, PollingEvent.COMPLETE);
		// Selection action
		listView.registerViewAction(new ViewAction<ListView<T>, ListEvent>() {
			@Override
			public void execute(final ListView<T> view, final ListEvent viewEvent) {
				handleSelection(viewEvent);
			}
		}, ListEvent.EDIT);

		holder.add(criteriaView);
		holder.add(ajaxPanel);
		ajaxPanel.add(pollingView);
		ajaxPanel.add(listView);

		// Default visibility
		pollingView.setVisible(false);
		listView.setVisible(false);

		// AJAX Targets
		criteriaView.addEventAjaxTarget(messages);
		pollingView.addEventAjaxTarget(messages);
		listView.addEventAjaxTarget(messages);

		criteriaView.addEventAjaxTarget(ajaxPanel);
		pollingView.addEventAjaxTarget(ajaxPanel);
	}

	public CriteriaView<S> getCriteriaView() {
		return criteriaView;
	}

	public PollingView<S, List<T>> getPollingView() {
		return pollingView;
	}

	public ListView<T> getListView() {
		return listView;
	}

	protected void handleCriteria(final S criteria) {

	}

	protected void handleSearchResult(final List<T> result) {

	}

	protected void handleSelection(final ListEvent event) {

	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
