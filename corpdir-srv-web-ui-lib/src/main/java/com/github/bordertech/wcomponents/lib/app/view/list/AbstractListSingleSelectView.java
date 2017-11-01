package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.collection.AbstractCollectionSingleSelectView;
import java.util.List;

/**
 * Default single select list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public class AbstractListSingleSelectView<T> extends AbstractCollectionSingleSelectView<T, List<T>> implements ListView<T>, SelectSingleView<T, List<T>> {

//	@Override
//	public void updateItem(final T item) {
//		List<T> items = getItems();
//		int idx = items.indexOf(item);
//		if (idx > -1) {
//			items.remove(idx);
//			items.add(idx, item);
//		} else {
//			items.add(item);
//		}
//		refreshItems(items);
//	}
}
