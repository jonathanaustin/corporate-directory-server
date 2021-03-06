package com.github.bordertech.flux.wc.view.smart.list;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.dumb.ListView;
import com.github.bordertech.flux.wc.view.smart.polling.AbstractPollingRetrieveSmartView;
import java.util.List;

/**
 * Polling View and List View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public abstract class AbstractListSmartView<S, T> extends AbstractPollingRetrieveSmartView<S, List<T>, List<T>> implements ListView<T> {

	private final ListView<T> listView;

	public AbstractListSmartView(final String viewId, final ListView<T> listView) {
		super(viewId, TemplateConstants.TEMPLATE_LIST_CRIT);
		// Add to Template
		this.listView = listView;
		addComponentToTemplate(TemplateConstants.TAG_VW_LIST, listView);
	}

	public ListView<T> getListView() {
		return listView;
	}

	@Override
	protected void handleRetrieveOKEvent(final List<T> items) {
		setItems(items);
	}

	@Override
	public List<T> getViewBean() {
		return listView.getViewBean();
	}

	@Override
	public void setViewBean(final List<T> viewBean) {
		listView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		listView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return listView.validateView();
	}

	@Override
	public void setItems(final List<T> items) {
		listView.setItems(items);
	}

	@Override
	public List<T> getItems() {
		return listView.getItems();
	}

	@Override
	public void refreshItems(final List<T> items) {
		listView.refreshItems(items);
	}

	@Override
	public void addItem(T item) {
		listView.addItem(item);
	}

	@Override
	public void removeItem(T item) {
		listView.removeItem(item);
	}

	@Override
	public void updateItem(T item) {
		listView.updateItem(item);
	}

}
