package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Criteria events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class CriteriaEvent implements Event {

	public static final List<Class<? extends Event>> EVENTS;

	static {
		List<Class<? extends Event>> items = new ArrayList<>();
		items.add(CriteriaEvent.Search.class);
		EVENTS = Collections.unmodifiableList(items);
	}

	protected CriteriaEvent() {
	}

	public static class Search<T> extends CriteriaEvent {

		private final T criteria;

		public Search(final T criteria) {
			this.criteria = criteria;
		}

		public T getCriteria() {
			return criteria;
		}
	};

}
