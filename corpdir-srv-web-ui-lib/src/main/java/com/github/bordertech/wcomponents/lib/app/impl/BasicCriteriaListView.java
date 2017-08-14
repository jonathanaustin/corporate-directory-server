package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.ctrl.CriteriaListCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.polling.PollingServiceView;
import com.github.bordertech.wcomponents.lib.polling.PollingException;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class BasicCriteriaListView<T> extends DefaultView implements MessageContainer {

	private final WMessages messages = new WMessages();

	public BasicCriteriaListView(final BasicController ctrl) {
		super(ctrl);

		CriteriaListCtrl viewCtrl = new CriteriaListCtrl(getDispatcher());

		CriteriaView criteriaView = new BasicCriteriaView(viewCtrl);
		ListView listView = new BasicListView(viewCtrl);

		PollingServiceView pollingView = new PollingServiceView<String, List<T>>(viewCtrl) {
			@Override
			public List<T> doPollingServiceAction(final String criteria) throws PollingException {
				return doSearchServiceCall(criteria);
			}
		};

		WDiv holder = getViewHolder();

		holder.add(viewCtrl);
		holder.add(messages);
		holder.add(criteriaView);
		holder.add(pollingView);
		holder.add(listView);

		viewCtrl.setCriteriaView(criteriaView);
		viewCtrl.setPollingView(pollingView);
		viewCtrl.setListView(listView);
	}

	abstract protected List<T> doSearchServiceCall(final String criteria);

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
