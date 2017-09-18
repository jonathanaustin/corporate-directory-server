package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import java.util.Collection;

/**
 * List View with a Text Search View.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <C> the collection type
 */
public class CollectionWithCriteriaTextView<T, C extends Collection<T>> extends CollectionWithCriteriaView<String, T, C> {

	public CollectionWithCriteriaTextView(final CollectionView<T, C> collectionView) {
		super(new SearchTextView(), collectionView);
	}
}
