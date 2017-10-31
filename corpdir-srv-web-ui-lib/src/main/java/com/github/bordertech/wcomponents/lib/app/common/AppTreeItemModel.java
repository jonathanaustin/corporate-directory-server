package com.github.bordertech.wcomponents.lib.app.common;

import com.github.bordertech.wcomponents.AbstractTreeItemModel;
import com.github.bordertech.wcomponents.lib.app.model.TreeModel;
import com.github.bordertech.flux.wc.DataApiProviderFactory;
import com.github.bordertech.wcomponents.util.TableUtil;
import java.util.List;
import java.util.Objects;

public class AppTreeItemModel<S, T> extends AbstractTreeItemModel {

	private final T EMPTY_ITEM = null;
	private final List<T> rootItems;
	private final TreeModel<S, T> model;

	public AppTreeItemModel(final List<T> rootItems, final String treeModelKey) {
		this.rootItems = rootItems;
		// TODO Pass this in (no dependency on flux package)
		this.model = (TreeModel<S, T>) DataApiProviderFactory.getInstance().getDataApi(treeModelKey);
	}

	public String getItemId(final T item) {
		return model.getItemId(item);
	}

	@Override
	public String getItemId(final List<Integer> row) {
		T item = getItem(row);
		String id = model.getItemId(item);
		return id == null ? TableUtil.rowIndexListToString(row) : id;
	}

	@Override
	public String getItemLabel(final List<Integer> row) {
		T item = getItem(row);
		return model.getItemLabel(item);
	}

	@Override
	public int getRowCount() {
		return getRootItems().size();
	}

	@Override
	public int getChildCount(final List<Integer> row) {
		T item = getItem(row);
		return loadChildren(item).size();
	}

	@Override
	public boolean hasChildren(final List<Integer> row) {
		T item = getItem(row);
		return model.hasChildren(item);
	}

	@Override
	public boolean isDisabled(final List<Integer> row) {
		T item = getItem(row);
		return Objects.equals(item, EMPTY_ITEM);
	}

	public T getItem(final List<Integer> row) {
		T item = getRootItems().get(row.get(0));
		for (int i = 1; i < row.size(); i++) {
			item = loadChildren(item).get(row.get(i));
		}
		return item;
	}

	protected List<T> getRootItems() {
		return rootItems;
	}

	protected List<T> loadChildren(final T item) {
		return model.getChildren(item);
	}

}
