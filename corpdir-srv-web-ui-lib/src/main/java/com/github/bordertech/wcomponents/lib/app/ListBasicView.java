package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * Basic list view.
 *
 * @author Jonathan Austin
 * @param <T> the entity type
 * @since 1.0.0
 */
public class ListBasicView<T> extends DefaultListView<T> {

	private final WList list = new WList(WList.Type.STACKED);

	public ListBasicView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public ListBasicView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
		list.setRepeatedComponent(new WText());
		list.setSeparator(WList.Separator.DOT);
		list.setBeanProperty(".");
		getContent().add(list);
	}

	public final WList getList() {
		return list;
	}

}
