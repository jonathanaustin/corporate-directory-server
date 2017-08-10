package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Navigation events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class NavEvent implements Event {

	public static final List<Class<? extends Event>> EVENTS;

	static {
		List<Class<? extends Event>> items = new ArrayList<>();
		items.add(First.class);
		items.add(Prev.class);
		items.add(Next.class);
		items.add(Last.class);
		EVENTS = Collections.unmodifiableList(items);
	}

	private final int idx;

	protected NavEvent(final int idx) {
		this.idx = idx;
	}

	public int getIdx() {
		return idx;
	}

	public static class First extends NavEvent {

		public First(final int idx) {
			super(idx);
		}
	};

	public static class Prev extends NavEvent {

		public Prev(final int idx) {
			super(idx);
		}
	};

	public static class Next extends NavEvent {

		public Next(final int idx) {
			super(idx);
		}
	};

	public static class Last extends NavEvent {

		public Last(final int idx) {
			super(idx);
		}
	};
}
