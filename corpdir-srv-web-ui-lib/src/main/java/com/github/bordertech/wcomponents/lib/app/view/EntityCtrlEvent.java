package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity ctrl events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class EntityCtrlEvent implements Event {

	public static final List<Class<? extends Event>> EVENTS;

	static {
		List<Class<? extends Event>> items = new ArrayList<>();
		items.add(EntityCtrlEvent.Back.class);
		items.add(EntityCtrlEvent.Edit.class);
		items.add(EntityCtrlEvent.Cancel.class);
		items.add(EntityCtrlEvent.Refresh.class);
		items.add(EntityCtrlEvent.Delete.class);
		items.add(EntityCtrlEvent.Save.class);
		EVENTS = Collections.unmodifiableList(items);
	}

	protected EntityCtrlEvent() {
	}

	public static class Back extends EntityCtrlEvent {
	};

	public static class Edit extends EntityCtrlEvent {
	};

	public static class Cancel extends EntityCtrlEvent {
	};

	public static class Refresh extends EntityCtrlEvent {
	};

	public static class Delete extends EntityCtrlEvent {
	};

	public static class Save extends EntityCtrlEvent {
	};
}
