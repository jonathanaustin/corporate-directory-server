package com.github.bordertech.wcomponents.lib.app.view.collection;

import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import java.util.Collection;

/**
 * Default single select view.
 *
 * @param <T> the Item type
 * @param <C> the Collection Type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractCollectionSingleSelectView<T, C extends Collection<T>> extends AbstractCollectionSelectableView<T, C> implements SelectSingleView<T, C> {

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

	@Override
	public void removeItem(final T entity) {
		super.removeItem(entity);
		clearSelected();
	}

	protected void doDispatchSelectEvent() {
		T bean = getSelectedItem();
		if (bean == null) {
			dispatchEvent(SelectEventType.UNSELECT);
		} else {
			dispatchEvent(SelectEventType.SELECT, bean);
		}
	}

	protected void registerSelectUnselectAjaxControl(final AppAjaxControl ctrl) {
		registerEventAjaxControl(SelectEventType.UNSELECT, ctrl);
		registerEventAjaxControl(SelectEventType.SELECT, ctrl);
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
