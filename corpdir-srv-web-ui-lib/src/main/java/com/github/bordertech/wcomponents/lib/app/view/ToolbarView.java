package com.github.bordertech.wcomponents.lib.app.view;

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

	void addToolbarItem(final ToolbarItem... types);

	void removeToolbarItem(final ToolbarItem... types);

	void clearToolbarItems();

	Set<ToolbarItem> getToolbarItems();

	boolean isUseToolbarItem(final ToolbarItem type);

}
