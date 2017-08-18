package com.github.bordertech.wcomponents.lib.app;

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
public class DefaultListView<T> extends DefaultView<List<T>> implements ListView<T> {

	public DefaultListView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultListView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
	}

}
