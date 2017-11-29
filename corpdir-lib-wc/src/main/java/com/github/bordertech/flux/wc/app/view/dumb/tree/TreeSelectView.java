package com.github.bordertech.flux.wc.app.view.dumb.tree;

import com.github.bordertech.flux.wc.app.view.SelectSingleView;

/**
 *
 * @author jonathan
 */
public interface TreeSelectView<T> extends SelectSingleView<T> {

	void setTreeItemModel(final TreeViewItemModel treeItemModel);

	TreeViewItemModel getTreeItemModel();
}
