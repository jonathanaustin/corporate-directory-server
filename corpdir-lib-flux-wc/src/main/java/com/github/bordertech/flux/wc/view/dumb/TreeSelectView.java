package com.github.bordertech.flux.wc.view.dumb;

/**
 *
 * @author jonathan
 */
public interface TreeSelectView<T> extends SelectSingleView<T> {

	void setTreeItemModel(final TreeViewItemModel treeItemModel);

	TreeViewItemModel getTreeItemModel();
}
