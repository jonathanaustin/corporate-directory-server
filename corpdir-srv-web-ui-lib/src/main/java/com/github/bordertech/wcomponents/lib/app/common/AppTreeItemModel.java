package com.github.bordertech.wcomponents.lib.app.common;

import com.github.bordertech.wcomponents.AbstractTreeItemModel;
import com.github.bordertech.wcomponents.lib.app.model.TreeModel;
import com.github.bordertech.wcomponents.lib.mvc.impl.ModelProviderFactory;
import com.github.bordertech.wcomponents.util.TableUtil;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AppTreeItemModel<T> extends AbstractTreeItemModel {

	private final T EMPTY_ITEM = null;
	private final List<T> EMPTY_ITEMS = Collections.EMPTY_LIST;

	private final List<T> rootItems;
	private final TreeModel<T> model;

	public AppTreeItemModel(final List<T> rootItems, final String treeModelKey) {
		this.rootItems = rootItems == null ? EMPTY_ITEMS : rootItems;
		this.model = (TreeModel<T>) ModelProviderFactory.getInstance().getModel(treeModelKey);
	}

	@Override
	public String getItemId(final List<Integer> row) {
		return TableUtil.rowIndexListToString(row);
	}

	@Override
	public String getItemLabel(final List<Integer> row) {
		T item = getItem(row);
		return model.getItemLabel(item);
	}

	@Override
	public int getRowCount() {
		return rootItems.size();
	}

	@Override
	public int getChildCount(final List<Integer> row) {
		T item = getItem(row);
		return model.getChildren(item).size();
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
		T item = rootItems.get(row.get(1));
		for (int i = 2; i < row.size(); i++) {
			item = model.getChildren(item).get(row.get(i));
		}
		return item;
	}

}
