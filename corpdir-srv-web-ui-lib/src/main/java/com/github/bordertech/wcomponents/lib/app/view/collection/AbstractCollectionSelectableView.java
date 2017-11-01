package com.github.bordertech.wcomponents.lib.app.view.collection;

import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
import com.github.bordertech.wcomponents.lib.app.view.SelectableView;
import java.util.Collection;

/**
 * Default selectable collection view.
 *
 * @param <T> the Item type
 * @param <C> the Collection Type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractCollectionSelectableView<T, C extends Collection<T>> extends AbstractCollectionView<T, C> implements SelectableView<T, C> {

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
