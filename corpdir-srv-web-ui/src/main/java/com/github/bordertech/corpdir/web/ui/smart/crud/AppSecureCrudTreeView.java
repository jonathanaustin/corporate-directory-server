package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultSecureCrudTreeView;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.security.DefaultAppPath;

/**
 * Default secure CRUD Tree View.
 *
 * @author jonathan
 */
public class AppSecureCrudTreeView<S, T> extends DefaultSecureCrudTreeView<S, T> {

	private final CardType cardType;

	public AppSecureCrudTreeView(final CardType cardType, final String viewId, final String title, final WComponent panel) {
		super(new DefaultAppPath(cardType.getPath()), viewId, title, panel);
		this.cardType = cardType;
	}

	public CardType getCardType() {
		return cardType;
	}

}
