package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import java.util.List;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 */
public abstract class AbstractListView<T> extends DefaultView<List<T>> implements ListView<T> {

	public AbstractListView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public AbstractListView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
	}

}
