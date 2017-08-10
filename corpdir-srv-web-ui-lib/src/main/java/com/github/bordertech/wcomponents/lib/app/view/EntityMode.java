package com.github.bordertech.wcomponents.lib.app.view;

import java.io.Serializable;

/**
 * Entity modes.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface EntityMode extends Serializable {

	EntityMode VIEW = new EntityMode() {
	};
	EntityMode EDIT = new EntityMode() {
	};
	EntityMode DELETE = new EntityMode() {
	};
	EntityMode CREATE = new EntityMode() {
	};
}
