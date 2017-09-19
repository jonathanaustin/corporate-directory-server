package com.github.bordertech.wcomponents.lib.app.view.combo.collection;

import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import java.util.Collection;

/**
 * Collection View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <C> the collection type
 */
public class CollectionWithSearchTextView<T, C extends Collection<T>> extends CollectionWithSearchView<String, T, C> {

	public CollectionWithSearchTextView(final CollectionView<T, C> collectionView) {
		super(new SearchTextView(), collectionView);
	}
}
