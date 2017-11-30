package com.github.bordertech.flux.wc.app.view.smart.tree;

import com.github.bordertech.flux.crud.store.EntityTreeStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.SelectMode;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.dumb.select.MenuSelectView;
import com.github.bordertech.flux.wc.app.view.dumb.tree.DefaultTreeSelectView;
import com.github.bordertech.flux.wc.app.view.dumb.tree.TreeSelectView;
import com.github.bordertech.flux.wc.app.view.dumb.tree.TreeViewItemModel;
import com.github.bordertech.flux.wc.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.Request;
import java.util.List;

/**
 * List or Tree View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class DefaultListOrTreeSmartView<T> extends DefaultMessageSmartView<List<T>> implements ListOrTreeSmartView<T> {

	private final SelectSingleView<T> listView;

	private final TreeSelectView<T> treeView;

	public DefaultListOrTreeSmartView(final String viewId) {
		this(viewId, new MenuSelectView<T>("vw_list"), new DefaultTreeSelectView<T>("vw_tree"));
	}

	public DefaultListOrTreeSmartView(final String viewId, final SelectSingleView<T> listView, final TreeSelectView<T> treeView) {
		super(viewId, "wclib/hbs/layout/combo-list-tree.hbs");

		// Views
		this.listView = listView;
		this.treeView = treeView;

		addComponentToTemplate("vw-list", listView);
		addComponentToTemplate("vw-tree", treeView);

		// Defualt visibility
		listView.setVisible(false);
		treeView.setVisible(false);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		if (isUseTree()) {
			treeView.setVisible(true);
			List<T> rootItems = getItems();
			if (rootItems != null && !rootItems.isEmpty()) {
				EntityTreeStore store = getEntityTreeStore();
				if (store == null) {
					throw new IllegalStateException("No entity tree store available.");
				}
				TreeViewItemModel model = new EntityStoreTreeItemModel(rootItems, store);
				treeView.setTreeItemModel(model);
			}
		} else {
			listView.setVisible(true);
		}
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
	}

	@Override
	public String getEntityTreeStoreKey() {
		return getComponentModel().entityTreeStoreKey;
	}

	@Override
	public void setEntityTreeStoreKey(final String entityStoreKey) {
		getOrCreateComponentModel().entityTreeStoreKey = entityStoreKey;
	}

	@Override
	public EntityTreeStore<T> getEntityTreeStore() {
		return StoreUtil.getStore(getEntityTreeStoreKey());
	}

	public final SelectSingleView<T> getListView() {
		return listView;
	}

	public final TreeSelectView<T> getTreeView() {
		return treeView;
	}

	@Override
	public boolean isUseTree() {
		return getComponentModel().useTree;
	}

	@Override
	public void setUseTree(final boolean useTree) {
		getOrCreateComponentModel().useTree = useTree;
	}

	@Override
	public T getSelectedItem() {
		return getCurrentView().getSelectedItem();
	}

	@Override
	public void setSelectedItem(final T item) {
		getCurrentView().setSelectedItem(item);
	}

	@Override
	public void clearSelected() {
		getCurrentView().clearSelected();
	}

	@Override
	public SelectMode getSelectMode() {
		return getCurrentView().getSelectMode();
	}

	@Override
	public void setSelectMode(final SelectMode mode) {
		getCurrentView().setSelectMode(mode);
	}

	@Override
	public boolean isViewMode() {
		return getCurrentView().isViewMode();
	}

	@Override
	public void setItems(final List<T> items) {
		getCurrentView().setItems(items);
	}

	@Override
	public List<T> getItems() {
		return getCurrentView().getItems();
	}

	@Override
	public void refreshItems(final List<T> items) {
		getCurrentView().refreshItems(items);
	}

	@Override
	public void addItem(final T item) {
		getCurrentView().addItem(item);
	}

	@Override
	public void removeItem(final T item) {
		getCurrentView().removeItem(item);
	}

	@Override
	public void updateItem(final T item) {
		getCurrentView().updateItem(item);
	}

	protected SelectSingleView<T> getCurrentView() {
		return isUseTree() ? getTreeView() : getListView();
	}

	@Override
	protected ListTreeSelectModel newComponentModel() {
		return new ListTreeSelectModel();
	}

	@Override
	protected ListTreeSelectModel getComponentModel() {
		return (ListTreeSelectModel) super.getComponentModel();
	}

	@Override
	protected ListTreeSelectModel getOrCreateComponentModel() {
		return (ListTreeSelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ListTreeSelectModel extends SmartViewModel {

		private String entityTreeStoreKey;

		private boolean useTree;
	}

}
