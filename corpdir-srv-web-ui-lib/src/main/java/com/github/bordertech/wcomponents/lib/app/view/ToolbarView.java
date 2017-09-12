package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarItemType;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.Set;

/**
 * Toolbar view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 *
 */
public interface ToolbarView<T> extends View<T> {

	void addToolbarType(final ToolbarItemType... types);

	void removeToolbarType(final ToolbarItemType... types);

	void clearToolbarTypes();

	Set<ToolbarItemType> getToolbarTypes();

	boolean isUseToolbarType(final ToolbarItemType type);

}
