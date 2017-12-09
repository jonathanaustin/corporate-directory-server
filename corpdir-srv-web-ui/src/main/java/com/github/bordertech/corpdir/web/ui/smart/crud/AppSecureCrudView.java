package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultSecureCrudView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.security.DefaultAppPath;

/**
 * Default secure CRUD view.
 *
 * @author jonathan
 */
public class AppSecureCrudView<T> extends DefaultSecureCrudView<String, T> {

	private final CardType cardType;

	public AppSecureCrudView(final CardType cardType, final String viewId, final String title, final WComponent panel) {
		super(new DefaultAppPath(cardType.getPath()), viewId, title, panel);
		this.cardType = cardType;
	}

	public CardType getCardType() {
		return cardType;
	}

	@Override
	protected String getRequestCriteria(final Request request) {
		return request.getParameter("id");
	}

}
