package com.github.bordertech.flux.wc.app.view.list;

import com.github.bordertech.flux.wc.app.mode.SelectMode;
import com.github.bordertech.flux.wc.app.view.SelectableView;

/**
 * Default single select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class AbstractListSelectableView<T> extends AbstractListView<T> implements SelectableView<T> {

	public AbstractListSelectableView(final String viewId) {
		super(viewId);
	}

	@Override
	public void setSelectMode(final SelectMode mode) {
		getOrCreateComponentModel().selectMode = mode == null ? SelectMode.VIEW : mode;
	}

	@Override
	public SelectMode getSelectMode() {
		return getComponentModel().selectMode;
	}

	@Override
	public boolean isViewMode() {
		return getSelectMode() == SelectMode.VIEW;
	}

	@Override
	protected SelectableModel newComponentModel() {
		return new SelectableModel();
	}

	@Override
	protected SelectableModel getComponentModel() {
		return (SelectableModel) super.getComponentModel();
	}

	@Override
	protected SelectableModel getOrCreateComponentModel() {
		return (SelectableModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class SelectableModel extends ViewModel {

		private SelectMode selectMode = SelectMode.VIEW;
	}

}
