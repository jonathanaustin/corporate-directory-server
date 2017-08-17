package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.app.mode.ListMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.List;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class SelectListBaseView<T> extends ListBaseView<T> implements SelectListView<T> {

	public SelectListBaseView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public SelectListBaseView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
	}

	@Override
	public void setListMode(final ListMode mode) {
		getOrCreateComponentModel().listMode = mode == null ? ListMode.VIEW : mode;
		handleViewState();
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
		getOrCreateComponentModel().selectedIdx = idx;
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
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		handleViewState();
	}

	protected void handleViewState() {
		// Do nothing
	}

	@Override
	protected ListViewModel newComponentModel() {
		return new ListViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListViewModel getComponentModel() {
		return (ListViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListViewModel getOrCreateComponentModel() {
		return (ListViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ListViewModel extends ViewModel {

		private ListMode listMode = ListMode.VIEW;

		private int selectedIdx = -1;
	}

}
