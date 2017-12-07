package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.wc.common.FluxMenuItem;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;

/**
 * Toolbar default implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultToolbarView<T> extends AbstractMenuToolbarView<T> {

	private final WMenuItem itemBack = new FluxMenuItem("Back", ToolbarBaseEventType.BACK) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarNavigationItemType.BACK);
		}
	};

	private final WMenuItem itemAdd = new FluxMenuItem("Add", ToolbarBaseEventType.ADD) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.ADD);
		}
	};

	private final WMenuItem itemReset = new FluxMenuItem("Reset", ToolbarBaseEventType.RESET) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarNavigationItemType.RESET);
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
		ViewUtil.addImageToMenuItem(IconConstants.CANCEL_IMAGE, itemBack, true);
		ViewUtil.addImageToMenuItem(IconConstants.ADD_IMAGE, itemAdd, true);
		ViewUtil.addImageToMenuItem(IconConstants.UNDO_IMAGE, itemReset, true);

		// Default to use RESET
		addToolbarItem(ToolbarNavigationItemType.RESET);
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
