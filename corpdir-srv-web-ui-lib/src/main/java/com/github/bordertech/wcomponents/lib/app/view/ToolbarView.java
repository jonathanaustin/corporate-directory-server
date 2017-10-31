package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.flux.wc.view.AppView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarItem;
import java.util.Set;

/**
 * Toolbar view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 *
 */
public interface ToolbarView<T> extends AppView<T> {

	void addToolbarItem(final ToolbarItem... types);

	void removeToolbarItem(final ToolbarItem... types);

	void clearToolbarItems();

	Set<ToolbarItem> getToolbarItems();

	boolean isUseToolbarItem(final ToolbarItem type);

}
