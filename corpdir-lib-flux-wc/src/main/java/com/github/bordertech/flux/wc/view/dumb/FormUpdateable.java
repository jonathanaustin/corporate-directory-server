package com.github.bordertech.flux.wc.view.dumb;

import com.github.bordertech.wcomponents.WComponent;

/**
 * Updateable form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface FormUpdateable extends WComponent {

	void doMakeFormReadonly(final boolean readonly);

}
