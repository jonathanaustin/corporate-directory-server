package com.github.bordertech.flux.wc.view.dumb;

import com.github.bordertech.flux.wc.view.dumb.toolbar.ToolbarItem;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import java.util.Set;

/**
 * Toolbar view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 *
 */
public interface ToolbarView<T> extends FluxDumbView<T> {

	void addToolbarItem(final ToolbarItem... types);

	void removeToolbarItem(final ToolbarItem... types);

	void clearToolbarItems();

	Set<ToolbarItem> getToolbarItems();

	boolean isUseToolbarItem(final ToolbarItem type);

}
