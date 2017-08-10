package com.github.bordertech.wcomponents.lib.app.view;

import java.io.Serializable;

/**
 * List modes.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ListMode extends Serializable {

	ListMode VIEW = new ListMode() {
	};
	ListMode EDIT = new ListMode() {
	};
}
