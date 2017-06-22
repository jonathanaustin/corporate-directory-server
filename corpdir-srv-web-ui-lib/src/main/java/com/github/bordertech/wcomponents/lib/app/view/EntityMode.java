package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.ViewMode;

/**
 * Entity modes.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface EntityMode extends ViewMode {

	EntityMode VIEW = new EntityMode() {
	};
	EntityMode EDIT = new EntityMode() {
	};
	EntityMode DELETE = new EntityMode() {
	};
	EntityMode CREATE = new EntityMode() {
	};
}
