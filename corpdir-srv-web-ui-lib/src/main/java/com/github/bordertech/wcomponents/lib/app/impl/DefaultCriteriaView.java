package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.lib.app.event.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;

/**
 * Default criteria view.
 *
 * @param <T> the criteria type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultCriteriaView<T> extends DefaultView implements CriteriaView<T> {

	public DefaultCriteriaView(final Dispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public T getCriteria() {
		return getComponentModel().criteria;
	}

	protected void setCriteria(final T criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	protected void handleCriteriaEvent() {
		Event event = new Event(this, CriteriaEvent.SEARCH, getCriteria());
		getDispatcher().dispatch(event);
	}

	@Override
	protected EntityViewModel<T> newComponentModel() {
		return new EntityViewModel();
	}

	@Override
	protected EntityViewModel<T> getComponentModel() {
		return (EntityViewModel) super.getComponentModel();
	}

	@Override
	protected EntityViewModel<T> getOrCreateComponentModel() {
		return (EntityViewModel) super.getOrCreateComponentModel();
	}

	public static class EntityViewModel<T> extends ViewModel {

		private T criteria;
	}

}
