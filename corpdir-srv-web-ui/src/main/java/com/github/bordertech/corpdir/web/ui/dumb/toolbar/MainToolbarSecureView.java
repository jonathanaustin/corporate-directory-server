package com.github.bordertech.corpdir.web.ui.dumb.toolbar;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.wc.common.FluxMenuItem;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.flux.wc.view.dumb.toolbar.AbstractMenuToolbarView;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.lib.security.MenuItemSecurePath;
import java.util.Objects;

/**
 * Main toolbar view.
 *
 * @author jonathan
 */
public class MainToolbarSecureView extends AbstractMenuToolbarView {

	private final WMenu menu = getMenu();

	public MainToolbarSecureView(final String viewId) {
		super(viewId);
		// Setup Menu Items
		WSubMenu subMenu = new WSubMenu("System");
		ViewUtil.addImageToLabelBody(IconConstants.SETTING_IMAGE, subMenu.getDecoratedLabel(), true);
		subMenu.setToolTip("System");

		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		subMenu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);

		for (CardType card : CardType.values()) {
			WMenuItem item = new MenuItemSecurePath(card.getDesc(), card.getPath());
			item.setToolTip(card.getDesc());
			if (card.getImageUrl() != null) {
				ViewUtil.addImageToLabelBody(card.getImageUrl(), item.getDecoratedLabel(), true);
			}
			if (card.isSystem()) {
				subMenu.add(item);
				item.addHtmlClass("fixsubmenu");
			} else {
				menu.add(item);
			}
		}
		menu.add(subMenu);

		//Default
		setCurrentCard(CardType.CONTACT);
	}

	public final void setCurrentCard(final CardType current) {
		menu.clearSelectedMenuItems();
		if (current != null) {
			for (MenuItem item : menu.getMenuItems(true)) {
				if (item instanceof FluxMenuItem && Objects.equals(current, ((FluxMenuItem) item).getItemData())) {
					menu.setSelectedMenuItem((FluxMenuItem) item);
					break;
				}
			}
		}
	}

	@Override
	protected void setupMenuAjax() {
		// No AJAX as navigating via URL
	}

}
