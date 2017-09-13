package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.corpdir.web.ui.icons.IconConstants;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.lib.app.common.AppMenuItem;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.AbstractMenuToolbar;
import com.github.bordertech.wcomponents.lib.util.ViewUtil;
import jersey.repackaged.com.google.common.base.Objects;

/**
 * Main toolbar view.
 *
 * @author jonathan
 */
public class MainToolbarView extends AbstractMenuToolbar {

	private final WMenu menu = getMenu();

	public MainToolbarView() {
		// Setup Menu Items
		WSubMenu subMenu = new WSubMenu("System");

		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		subMenu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);

		// Submenu Image
		ViewUtil.addImageToLabel(IconConstants.SETTING_IMAGE, subMenu.getDecoratedLabel());

		for (CardType card : CardType.values()) {
			WMenuItem item = new AppMenuItem(card.getDesc(), CardEventType.SHOW, card);
			if (card.getImageUrl() != null) {
				ViewUtil.addImageToMenuItem(card.getImageUrl(), item);
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
		setCurrentCard(CardType.CONTACT_CARD);

	}

	public final void setCurrentCard(final CardType current) {
		menu.clearSelectedMenuItems();
		if (current != null) {
			for (MenuItem item : menu.getMenuItems(true)) {
				if (item instanceof AppMenuItem && Objects.equal(current, ((AppMenuItem) item).getItemData())) {
					menu.setSelectedMenuItem((AppMenuItem) item);
					break;
				}
			}

		}
	}

}
