package com.github.bordertech.flux.wc.app.view.smart.tree;

import com.github.bordertech.flux.crud.store.EntityTreeStore;
import com.github.bordertech.flux.view.SmartView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import java.util.List;

/**
 * List or Tree Smart View.
 *
 * @author jonathan
 */
public interface ListOrTreeSmartView<T> extends SmartView<List<T>>, SelectSingleView<T> {

	boolean isUseTree();

	void setUseTree(final boolean useTree);

	String getEntityTreeStoreKey();

	void setEntityTreeStoreKey(final String entityTreeStoreKey);

	EntityTreeStore<T> getEntityTreeStore();
}
