package com.github.bordertech.wcomponents.lib.app.list;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.mode.ListMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Default select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class DefaultSelectView<T> extends DefaultListView<T> implements SelectView<T> {

	@Override
	public void setListMode(final ListMode mode) {
		getOrCreateComponentModel().listMode = mode == null ? ListMode.VIEW : mode;
	}

	@Override
	public ListMode getListMode() {
		return getComponentModel().listMode;
	}

	@Override
	public void clearSelected() {
		getOrCreateComponentModel().selected = null;
	}

	@Override
	public T getSelectedItem() {
		return getComponentModel().selected;
	}

	@Override
	public void setSelectedItem(final T entity) {
		// Find Bean
		if (getItemList().contains(entity)) {
			getOrCreateComponentModel().selected = entity;
		} else {
			getOrCreateComponentModel().selected = null;
		}

	}

	@Override
	public void removeItem(final T entity) {
		super.removeItem(entity);
		clearSelected();
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		setListMode(readonly ? ListMode.VIEW : ListMode.SELECT);
	}

	protected void doDispatchSelectEvent() {
		T bean = getSelectedItem();
		if (bean == null) {
			dispatchEvent(ListEventType.UNSELECT);
		} else {
			dispatchEvent(ListEventType.SELECT, bean);
		}
	}

	protected boolean isListModeView() {
		return getListMode() == ListMode.VIEW;
	}

	@Override
	public int getIndexOfItem(final T entity) {
		return getItemList().indexOf(entity);
	}

	@Override
	public T getItem(final int idx) {
		int size = getSize();
		if (idx < -1 || idx >= size) {
			throw new IllegalArgumentException("Get item by index is invalid. Index: " + idx + " size: " + size + ".");
		}
		return getItemList().get(idx);
	}

	@Override
	protected SelectModel<T> newComponentModel() {
		return new SelectModel();
	}

	@Override
	protected SelectModel<T> getComponentModel() {
		return (SelectModel) super.getComponentModel();
	}

	@Override
	protected SelectModel<T> getOrCreateComponentModel() {
		return (SelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class SelectModel<T> extends ViewModel {

		private ListMode listMode = ListMode.VIEW;

		private T selected;
	}

}
