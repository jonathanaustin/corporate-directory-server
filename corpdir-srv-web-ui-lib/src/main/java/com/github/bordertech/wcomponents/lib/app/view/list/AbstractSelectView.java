package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;

/**
 * Default select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class AbstractSelectView<T> extends AbstractListView<T> implements SelectView<T> {

	@Override
	public void setListMode(final SelectMode mode) {
		getOrCreateComponentModel().listMode = mode == null ? SelectMode.VIEW : mode;
	}

	@Override
	public SelectMode getListMode() {
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
		setListMode(readonly ? SelectMode.VIEW : SelectMode.SELECT);
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
		return getListMode() == SelectMode.VIEW;
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

		private SelectMode listMode = SelectMode.VIEW;

		private T selected;
	}

}
