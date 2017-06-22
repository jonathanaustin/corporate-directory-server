package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.ViewMode;

/**
 * List modes.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ListMode extends ViewMode {

	ListMode VIEW = new ListMode() {
	};
	ListMode EDIT = new ListMode() {
	};
}
