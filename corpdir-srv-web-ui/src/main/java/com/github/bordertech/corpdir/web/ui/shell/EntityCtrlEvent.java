package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Entity ctrl events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface EntityCtrlEvent extends ViewEvent {

	EntityCtrlEvent BACK = new EntityCtrlEvent() {
	};
	EntityCtrlEvent EDIT = new EntityCtrlEvent() {
	};
	EntityCtrlEvent CANCEL = new EntityCtrlEvent() {
	};
	EntityCtrlEvent REFRESH = new EntityCtrlEvent() {
	};
	EntityCtrlEvent DELETE = new EntityCtrlEvent() {
	};
	EntityCtrlEvent SAVE = new EntityCtrlEvent() {
	};
}
