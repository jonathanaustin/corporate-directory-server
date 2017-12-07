/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.bordertech.corpdir.web.ui.dumb.input;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.common.WLibLink;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class EntityLink<T extends ApiIdObject> extends WLibLink {

	private final CardType card;

	public EntityLink(final CardType card) {
		this.card = card;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			setupLink();
			setInitialised(true);
		}
	}

	protected void setupLink() {
		T bean = (T) getBean();
		// Build url to entity
		String path = EnvironmentHelper.prefixSecureServletPath(card.getPath());
		Map<String, String> params = new HashMap<>(1);
		params.put("id", bean.getId());
		String url = WebUtilities.getPath(path, params);
		// Setup link (base url flag)
		setUrl(url, true);
		setText(bean.getDescription());
		setOpenNewWindow(false);
	}

}
