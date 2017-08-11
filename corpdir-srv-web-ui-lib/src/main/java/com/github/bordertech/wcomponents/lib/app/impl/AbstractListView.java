package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import java.util.List;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractListView<T> extends DefaultView implements ListView<T> {

	public AbstractListView(final Dispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void setEntities(final List<T> entities) {
		setBean(entities);
	}

	@Override
	public List<T> getEntities() {
		return (List<T>) getBeanValue();
	}

}
