package com.github.bordertech.wcomponents.lib.util;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUpdateable;

/**
 * Default updateable view that can be used as a child a of a from view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class FormUtil {

	private FormUtil() {
	}

	public static void doMakeInputsReadonly(final WComponent component, final boolean readOnly) {
		if (component instanceof Input) {
			((Input) component).setReadOnly(readOnly);
		}
		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				if (child instanceof FormUpdateable) {
					((FormUpdateable) child).doMakeFormReadonly(readOnly);
				} else {
					doMakeInputsReadonly(child, readOnly);
				}
			}
		}
	}
}
