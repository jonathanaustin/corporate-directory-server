package com.github.bordertech.corpdir.web.ui.smart.main;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.flux.wc.common.AppMenuItem;
import com.github.bordertech.flux.wc.view.dumb.toolbar.AbstractMenuToolbarView;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WSubMenu;
import java.util.Objects;

/**
 * Main toolbar view.
 *
 * @author jonathan
 */
public class MainToolbarView extends AbstractMenuToolbarView {

	private final WMenu menu = getMenu();

	public MainToolbarView(final String viewId) {
		super(viewId);
		// Setup Menu Items
		WSubMenu subMenu = new WSubMenu("System");
		ViewUtil.addImageToLabelBody(IconConstants.SETTING_IMAGE, subMenu.getDecoratedLabel());
		subMenu.setToolTip("System");

		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		subMenu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);

		for (CardType card : CardType.values()) {
			WMenuItem item = new AppMenuItem(card.getDesc(), CardEventType.SHOW, card);
			item.setToolTip(card.getDesc());
			if (card.getImageUrl() != null) {
				ViewUtil.addImageToLabelBody(card.getImageUrl(), item.getDecoratedLabel());
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
				if (item instanceof AppMenuItem && Objects.equals(current, ((AppMenuItem) item).getItemData())) {
					menu.setSelectedMenuItem((AppMenuItem) item);
					break;
				}
			}

		}
	}

}
