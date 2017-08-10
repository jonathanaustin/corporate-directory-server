package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.View;

/**
 * Entity ctrl view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityCtrlView extends View {

	void setEntityMode(final EntityMode mode);

	EntityMode getEntityMode();

	void setEntityReady(final boolean entityReady);

	boolean isEntityReady();

	boolean isUseBack();

	void setUseBack(final boolean useBack);

	void doRefreshViewState();

}
