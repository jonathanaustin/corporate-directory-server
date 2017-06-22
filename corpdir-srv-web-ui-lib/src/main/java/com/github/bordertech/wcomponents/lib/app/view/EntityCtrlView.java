package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.BasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;

/**
 * Entity ctrl view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityCtrlView extends BasicEventView {

	void setEntityMode(final EntityMode mode);

	EntityMode getEntityMode();

	void setEntityReady(final boolean entityReady);

	boolean isEntityReady();

	void registerViewAction(final ViewAction<EntityCtrlView, EntityCtrlEvent> viewAction, final EntityCtrlEvent... viewEvent);

	void doRefreshViewState();

}
