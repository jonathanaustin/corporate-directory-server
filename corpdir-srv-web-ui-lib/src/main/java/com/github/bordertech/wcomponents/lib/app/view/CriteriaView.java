package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.flux.impl.WView;

/**
 * Entity Criteria View.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 *
 */
public interface CriteriaView<T> extends WView<T> {

	WButton getSearchButton();

}
