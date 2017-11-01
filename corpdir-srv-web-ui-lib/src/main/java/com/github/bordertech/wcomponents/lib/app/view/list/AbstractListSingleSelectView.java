package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.event.SelectViewEvent;

/**
 * Default single select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class AbstractListSingleSelectView<T> extends AbstractListSelectableView<T> implements SelectSingleView<T> {

	public AbstractListSingleSelectView(final String viewId) {
		super(viewId);
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
		getOrCreateComponentModel().selected = entity;
	}

//	@Override
//	public void removeItem(final T entity) {
//		super.removeItem(entity);
//		clearSelected();
//	}
	protected void doDispatchSelectEvent() {
		T bean = getSelectedItem();
		if (bean == null) {
			dispatchViewEvent(SelectViewEvent.UNSELECT);
		} else {
			dispatchViewEvent(SelectViewEvent.SELECT, bean);
		}
	}

	protected void registerSelectUnselectAjaxControl(final AppAjaxControl ctrl) {
		registerEventAjaxControl(SelectViewEvent.UNSELECT, ctrl);
		registerEventAjaxControl(SelectViewEvent.SELECT, ctrl);
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
	public static class SelectModel<T> extends SelectableModel {

		private T selected;
	}

}
