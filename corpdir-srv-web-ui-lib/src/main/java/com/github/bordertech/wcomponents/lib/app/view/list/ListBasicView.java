package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WText;

/**
 * Basic list view.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public class ListBasicView<T> extends AbstractListView<T> {

	private final WList list = new WList(WList.Type.STACKED);

	public ListBasicView() {
		list.setRepeatedComponent(new WText());
		list.setSeparator(WList.Separator.DOT);
		list.setBeanProperty(".");
		getContent().add(list);
	}

	public final WList getList() {
		return list;
	}

}
