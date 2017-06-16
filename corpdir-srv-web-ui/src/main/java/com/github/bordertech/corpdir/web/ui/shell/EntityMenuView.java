package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;

/**
 * Entity action menu.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityMenuView extends BasicView {

	void setEntityMode(final EntityMode mode);

	EntityMode getEntityMode();

	void setMenuAction(final EntityEvent event, final Action action);

	Action getMenuAction(final EntityEvent event);

}
