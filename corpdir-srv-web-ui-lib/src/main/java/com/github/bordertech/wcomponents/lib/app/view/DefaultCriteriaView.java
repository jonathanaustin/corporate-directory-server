package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;
import com.github.bordertech.wcomponents.lib.view.DefaultView;
import java.util.List;

/**
 * Default criteria view.
 *
 * @param <T> the criteria type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultCriteriaView<T> extends DefaultView implements CriteriaView<T> {

	@Override
	public List<Class<? extends Event>> getPublisherEvents() {
		return CriteriaEvent.EVENTS;
	}

	@Override
	public T getCriteria() {
		return getComponentModel().criteria;
	}

	protected void setCriteria(final T criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	protected void handleCriteriaEvent() {
		CriteriaEvent event = new CriteriaEvent.Search(getCriteria());
		notifySubscribers(event);
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
