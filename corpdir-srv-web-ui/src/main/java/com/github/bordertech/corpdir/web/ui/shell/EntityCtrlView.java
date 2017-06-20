package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Entity ctrl view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityCtrlView extends BasicEventView<EntityCtrlView, EntityCtrlEvent> {

	void setEntityMode(final EntityMode mode);

	EntityMode getEntityMode();

}
