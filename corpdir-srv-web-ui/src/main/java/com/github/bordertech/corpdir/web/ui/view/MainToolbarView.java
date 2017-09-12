package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.lib.app.common.AppMenuItem;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.AbstractMenuToolbar;

/**
 * Main toolbar view.
 *
 * @author jonathan
 */
public class MainToolbarView extends AbstractMenuToolbar {

	public MainToolbarView() {
		// Setup Menu Items
		WMenu menu = getMenu();
		WSubMenu subMenu = new WSubMenu("System");
		for (CardType card : CardType.values()) {
			WMenuItem item = new AppMenuItem(card.getDesc(), CardEventType.SHOW, card);
			if (card.isSystem()) {
				subMenu.add(item);
			} else {
				menu.add(item);
			}
		}
		menu.add(subMenu);

	}

}
