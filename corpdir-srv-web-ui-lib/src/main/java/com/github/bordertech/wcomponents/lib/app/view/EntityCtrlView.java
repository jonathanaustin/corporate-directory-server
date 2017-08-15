package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.impl.BasicView;

/**
 * Entity ctrl view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean
 * @since 1.0.0
 *
 */
public interface EntityCtrlView<T> extends BasicView<T> {

	EntityMode getEntityMode();

	void setEntityMode(final EntityMode mode);

	boolean isEntityReady();

	void setEntityReady(final boolean entityReady);

	boolean isUseBack();

	void setUseBack(final boolean useBack);

	void doRefreshViewState();

}
