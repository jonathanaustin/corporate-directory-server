package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.crud.store.CrudTreeStore;
import com.github.bordertech.flux.view.SmartView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import java.util.List;

/**
 * List or Tree Smart View.
 *
 * @author jonathan
 */
public interface ListOrTreeSelectView<K, T> extends SmartView<List<T>>, SelectSingleView<T> {

	boolean isUseTree();

	void setUseTree(final boolean useTree);

	// FIXME JA USE Consumer Interface
	String getEntityTreeStoreKey();

	void setEntityTreeStoreKey(final String entityTreeStoreKey);

	CrudTreeStore<?, K, T> getEntityTreeStore();
}
