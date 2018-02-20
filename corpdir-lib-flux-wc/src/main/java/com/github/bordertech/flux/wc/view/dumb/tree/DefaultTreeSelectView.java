package com.github.bordertech.flux.wc.view.dumb.tree;

import com.github.bordertech.flux.wc.common.FluxAjaxControl;
import com.github.bordertech.flux.wc.mode.SelectMode;
import com.github.bordertech.flux.wc.view.dumb.TreeSelectView;
import com.github.bordertech.flux.wc.view.dumb.TreeViewItemModel;
import com.github.bordertech.flux.wc.view.dumb.select.AbstractListSingleSelectView;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTree;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Single select tree view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultTreeSelectView<T> extends AbstractListSingleSelectView<T> implements TreeSelectView<T> {

	private final WTree tree = new WTree(WTree.Type.VERTICAL) {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			findTreeItem();
		}

		@Override
		public boolean isDisabled() {
			return isViewMode();
		}
	};

	private final FluxAjaxControl ajax = new FluxAjaxControl(tree);

	public DefaultTreeSelectView(final String viewId) {
		super(viewId);
		getContent().add(tree);
		tree.setSelectMode(WTree.SelectMode.SINGLE);
		setSelectMode(SelectMode.SELECT);
		getContent().add(ajax);
		registerSelectUnselectAjaxControl(ajax);
		tree.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleSelectedItem();
			}
		});
	}

	public final WTree getWTree() {
		return tree;
	}

	@Override
	public void removeItem(final T item) {
		List<T> items = getItems();
		// Check if on root items
		if (items.contains(item)) {
			items.remove(item);
		}
		refreshItems(items);
	}

	@Override
	public void updateItem(final T item) {
		// Do Nothing (as gets complicated with parent and child relationships changing
	}

	@Override
	public void clearSelected() {
		super.clearSelected();
		tree.setSelectedRows(null);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		TreeViewItemModel model = getTreeItemModel();
		if (model != null) {
			tree.setTreeModel(model);
		}
	}

	protected void handleSelectedItem() {
		setSelectedItem(getTreeSelectedItem());
		doDispatchSelectEvent();
	}

	protected T getTreeSelectedItem() {
		T selected = null;
		if (!tree.getSelectedRows().isEmpty()) {
			String rowId = tree.getSelectedRows().iterator().next();
			List<Integer> index = tree.getExpandedItemIdIndexMap().get(rowId);
			if (index != null) {
				selected = (T) getTreeItemModel().getItem(index);
			}
		}
		return selected;
	}

	protected void findTreeItem() {
		T selected = getSelectedItem();
		if (selected == null) {
			tree.setSelectedRows(null);
		} else if (!Objects.equals(selected, getTreeSelectedItem())) {
			String rowId = getTreeItemModel().getItemId(selected);
			tree.setSelectedRows(new HashSet<>(Arrays.asList(rowId)));
		}
	}

	@Override
	public void setTreeItemModel(final TreeViewItemModel treeItemModel) {
		getOrCreateComponentModel().treeItemModel = treeItemModel;
	}

	@Override
	public TreeViewItemModel getTreeItemModel() {
		return getComponentModel().treeItemModel;
	}

	@Override
	protected TreeSelectModel<T> newComponentModel() {
		return new TreeSelectModel();
	}

	@Override
	protected TreeSelectModel<T> getComponentModel() {
		return (TreeSelectModel) super.getComponentModel();
	}

	@Override
	protected TreeSelectModel<T> getOrCreateComponentModel() {
		return (TreeSelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class TreeSelectModel<T> extends SelectModel<T> {

		private TreeViewItemModel<T> treeItemModel;

	}

}
