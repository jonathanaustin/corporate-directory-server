package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListEvent;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.polling.AbstractPollingView;
import com.github.bordertech.wcomponents.lib.polling.PollingEvent;
import com.github.bordertech.wcomponents.lib.polling.PollingException;
import com.github.bordertech.wcomponents.lib.polling.PollingView;
import com.github.bordertech.wcomponents.lib.view.DefaultTemplateView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class AbstractSearchListController<S, T> extends DefaultTemplateView implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final CriteriaView<S> criteriaView;
	private final PollingView<S, List<T>> pollingView = new AbstractPollingView<S, List<T>>() {
		@Override
		public List<T> doPollingAction(final S criteria) throws PollingException {
			return doSearchServiceCall(criteria);
		}

	};

	private final ListView<T> listView;

	public AbstractSearchListController(final CriteriaView<S> criteriaView, final ListView<T> listView) {
		this.criteriaView = criteriaView;
		this.listView = listView;

		WTemplate holder = getViewTemplate();

		holder.addTaggedComponent("msg", messages);
		holder.addTaggedComponent("cv", criteriaView);
		holder.addTaggedComponent("pv", pollingView);
		holder.addTaggedComponent("lv", listView);

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

		// Default visibility
		pollingView.setVisible(false);
		listView.setVisible(false);

		// AJAX Targets
		addDefaultTarget(messages);
		addDefaultTarget(criteriaView);
		addDefaultTarget(pollingView);
		addDefaultTarget(listView);
	}

	private void addDefaultTarget(final AjaxTarget target) {
		criteriaView.addEventAjaxTarget(target);
		pollingView.addEventAjaxTarget(target);
		listView.addEventAjaxTarget(target);
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

	abstract protected List<T> doSearchServiceCall(final S criteria);

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
