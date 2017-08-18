package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.flux.impl.WView;

/**
 * Entity Criteria View.
 *
 * @author Jonathan Austin
 * @param <S> the criteria type
 * @since 1.0.0
 *
 */
public interface CriteriaView<S> extends WView<S> {

	WButton getSearchButton();

}
