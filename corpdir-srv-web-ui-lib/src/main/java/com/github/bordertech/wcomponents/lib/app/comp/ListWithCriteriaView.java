package com.github.bordertech.wcomponents.lib.app.comp;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.model.RequiresServiceModel;
import com.github.bordertech.wcomponents.lib.model.ServiceModel;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class ListWithCriteriaView<S, T> extends DefaultView<List<T>> implements MessageContainer, ListView<T>,
		RequiresServiceModel<S, List<T>> {

	private final WMessages messages = new WMessages();

	private final DefaultPollingView<S, List<T>> pollingView;

	private final ListWithCriteriaCtrl<S, T> ctrl;

	public ListWithCriteriaView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final ListView<T> listView) {
		super(dispatcher, qualifier);

		// Create controller
		this.ctrl = new ListWithCriteriaCtrl<>(dispatcher, qualifier);

		// Set views on Controller
		pollingView = new DefaultPollingView<>(dispatcher, qualifier);
		ctrl.setCriteriaView(criteriaView);
		ctrl.setPollingView(pollingView);
		ctrl.setListView(listView);

		// Add views to holder
		WDiv holder = getContent();
		holder.add(ctrl);
		holder.add(messages);
		holder.add(criteriaView);
		holder.add(pollingView);
		holder.add(listView);
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	public ServiceModel<S, List<T>> getServiceModel() {
		return ctrl.getServiceModel();
	}

	@Override
	public void setServiceModel(final ServiceModel<S, List<T>> serviceModel) {
		ctrl.setServiceModel(serviceModel);
	}

	public void configViews() {
		ctrl.configViews();
	}

}
