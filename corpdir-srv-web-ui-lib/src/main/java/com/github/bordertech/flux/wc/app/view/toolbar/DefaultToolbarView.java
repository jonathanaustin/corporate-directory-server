package com.github.bordertech.flux.wc.app.view.toolbar;

import com.github.bordertech.flux.wc.app.common.AppMenuItem;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;

/**
 * Toolbar default implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultToolbarView<T> extends AbstractMenuToolbar<T> {

	private final WMenuItem itemBack = new AppMenuItem("Back", ToolbarBaseViewEvent.BACK) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarNavigationItem.BACK);
		}
	};

	private final WMenuItem itemAdd = new AppMenuItem("Add", ToolbarBaseViewEvent.ADD) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelEventItem.ADD);
		}
	};

	private final WMenuItem itemReset = new AppMenuItem("Reset", ToolbarBaseViewEvent.RESET) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarNavigationItem.RESET);
		}
	};

	public DefaultToolbarView(final String viewId) {
		super(viewId);
		WMenu menu = getMenu();
		menu.add(itemBack);
		menu.add(itemAdd);
		menu.add(itemReset);
		menu.addHtmlClass("wc-neg-margin");

		// Images
		ViewUtil.addImageToMenuItem(IconConstants.CANCEL_IMAGE, itemBack);
		ViewUtil.addImageToMenuItem(IconConstants.ADD_IMAGE, itemAdd);
		ViewUtil.addImageToMenuItem(IconConstants.UNDO_IMAGE, itemReset);

		// Default to use RESET
		addToolbarItem(ToolbarNavigationItem.RESET);
	}

	public final WMenuItem getItemBack() {
		return itemBack;
	}

	public final WMenuItem getItemAdd() {
		return itemAdd;
	}

	public final WMenuItem getItemReset() {
		return itemReset;
	}

}
