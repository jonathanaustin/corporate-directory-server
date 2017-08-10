package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class ListEvent<T> implements Event {

	public static final List<Class<? extends Event>> EVENTS;

	static {
		List<Class<? extends Event>> items = new ArrayList<>();
		items.add(ListEvent.View.class);
		items.add(ListEvent.Edit.class);
		items.add(ListEvent.Delete.class);
		items.add(ListEvent.Select.class);
		EVENTS = Collections.unmodifiableList(items);
	}

	private final T item;

	protected ListEvent(final T item) {
		this.item = item;
	}

	public T getItem() {
		return item;
	}

	public static class View<T> extends ListEvent<T> {

		public View(final T item) {
			super(item);
		}
	};

	public static class Edit<T> extends ListEvent<T> {

		public Edit(final T item) {
			super(item);
		}
	};

	public static class Delete<T> extends ListEvent<T> {

		public Delete(final T item) {
			super(item);
		}
	};

	public static class Select<T> extends ListEvent<T> {

		public Select(final T item) {
			super(item);
		}
	};
}
