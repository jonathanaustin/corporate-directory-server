package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTree;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.common.AppTreeItemModel;
import com.github.bordertech.wcomponents.lib.app.mode.SelectMode;
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
public class TreeSelectView<T> extends AbstractListSingleSelectView<T> {

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

	private final AppAjaxControl ajax = new AppAjaxControl(tree);

	public TreeSelectView(final String viewId) {
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

//	@Override
//	public void removeItem(final T item) {
//		List<T> items = getItems();
//		// Check if on root items
//		if (items.contains(item)) {
//			items.remove(item);
//		}
//		refreshItems(items);
//	}
//
//	@Override
//	public void updateItem(final T item) {
//		// Do Nothing (as gets complicated with parent and child relationships changing
//	}
	@Override
	public void clearSelected() {
		super.clearSelected();
		tree.setSelectedRows(null);
	}

	@Override
	protected void initViewContent(final Request request
	) {
		super.initViewContent(request);
		List<T> beans = getViewBean();
//		if (beans != null && !beans.isEmpty()) {
//			TreeItemModel model = new AppTreeItemModel<>(beans, getTreeModelKey());
//			tree.setTreeModel(model);
//		}
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

	protected AppTreeItemModel getTreeItemModel() {
		return (AppTreeItemModel) tree.getTreeModel();
	}

//	@Override
//	public void setTreeModelKey(final String key) {
//		getOrCreateComponentModel().treeModelKey = key;
//	}
//
//	@Override
//	public String getTreeModelKey() {
//		return getComponentModel().treeModelKey;
//	}
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

		private String treeModelKey;

	}

}
