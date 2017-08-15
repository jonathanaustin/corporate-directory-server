package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.CriteriaListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.impl.ExecuteService;
import com.github.bordertech.wcomponents.lib.polling.PollingServiceView;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class BasicCriteriaListView<T> extends DefaultView<T> implements MessageContainer {

	private final WMessages messages = new WMessages();

	public BasicCriteriaListView(final BasicController ctrl) {
		super(ctrl);

		// Create controller
		CriteriaListCtrl<String, T> viewCtrl = new CriteriaListCtrl<>(getDispatcher());

		// Set views on Controller
		CriteriaView criteriaView = new BasicCriteriaView(viewCtrl);
		ListView listView = new BasicListView(viewCtrl);
		PollingServiceView pollingView = new PollingServiceView(viewCtrl);
		viewCtrl.setCriteriaView(criteriaView);
		viewCtrl.setPollingView(pollingView);
		viewCtrl.setListView(listView);

		// Search Service Action
		ExecuteService<String, List<T>> searchService = new ExecuteService<String, List<T>>() {
			@Override
			public List<T> executeService(final String criteria) {
				return doSearchServiceCall(criteria);
			}
		};
		viewCtrl.setSearchService(searchService);

		// Add views to holder
		WDiv holder = getViewHolder();
		holder.add(viewCtrl);
		holder.add(messages);
		holder.add(criteriaView);
		holder.add(pollingView);
		holder.add(listView);
	}

	/**
	 * Do the Search Service Call.
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param criteria the search criteria
	 * @return the search results
	 */
	abstract protected List<T> doSearchServiceCall(final String criteria);

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
