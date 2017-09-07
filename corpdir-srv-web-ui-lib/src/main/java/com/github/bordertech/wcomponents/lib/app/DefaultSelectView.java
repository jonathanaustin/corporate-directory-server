package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.mode.ListMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import java.util.List;

/**
 * Default select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class DefaultSelectView<T> extends DefaultListView<T> implements SelectView<T> {

	public DefaultSelectView() {
		this(null);
	}

	public DefaultSelectView(final String qualifier) {
		super(qualifier);
	}

	@Override
	public void setListMode(final ListMode mode) {
		getOrCreateComponentModel().listMode = mode == null ? ListMode.VIEW : mode;
	}

	@Override
	public ListMode getListMode() {
		return getComponentModel().listMode;
	}

	@Override
	public void setSelectedIdx(final int idx) {
		int size = getSize();
		if (idx < -1 || idx >= size) {
			throw new IllegalArgumentException("Selected index is invalid. Index: " + idx + " size: " + size + ".");
		}
		if (getSelectedIdx() != idx) {
			getOrCreateComponentModel().selectedIdx = idx;
		}
	}

	@Override
	public int getSelectedIdx() {
		return getComponentModel().selectedIdx;
	}

	@Override
	public void clearSelectedIdx() {
		if (getSelectedIdx() != -1) {
			getOrCreateComponentModel().selectedIdx = -1;
		}
	}

	@Override
	public int getSize() {
		List<T> items = getViewBean();
		return items == null ? 0 : items.size();
	}

	@Override
	public T getSelected() {
		int idx = getSelectedIdx();
		if (idx < 0) {
			return null;
		}
		List<T> items = getViewBean();
		return items.get(idx);
	}

	@Override
	public void setSelected(final T entity) {
		// Find Bean
		int idx = entity == null ? -1 : getViewBean().indexOf(entity);
		setSelectedIdx(idx);
	}

	@Override
	public void addItem(final T entity) {
		super.addItem(entity);
		setSelected(entity);
	}

	@Override
	public void removeItem(final T entity) {
		super.removeItem(entity);
		clearSelectedIdx();
	}

	@Override
	public void updateItem(final T entity) {
		super.updateItem(entity);
		setSelected(entity);
	}

	protected void doDispatchSelectEvent() {
		T bean = getSelected();
		dispatchViewEvent(ListEventType.SELECT, bean);
	}

	@Override
	protected SelectModel newComponentModel() {
		return new SelectModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SelectModel getComponentModel() {
		return (SelectModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SelectModel getOrCreateComponentModel() {
		return (SelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class SelectModel extends ViewModel {

		private ListMode listMode = ListMode.VIEW;

		private int selectedIdx = -1;
	}

}
