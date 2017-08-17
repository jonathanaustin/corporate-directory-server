package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * Default list view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicListView<T> extends AbstractListView<T> {

	private final WList list = new WList(WList.Type.STACKED);

	public BasicListView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public BasicListView(final Dispatcher dispatcher, final String qualifier) {
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
