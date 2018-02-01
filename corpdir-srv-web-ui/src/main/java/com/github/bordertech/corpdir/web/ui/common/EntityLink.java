package com.github.bordertech.corpdir.web.ui.common;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.common.WDiv;
import com.github.bordertech.wcomponents.lib.common.WLibLink;
import com.github.bordertech.wcomponents.lib.security.EnvironmentHelper;
import java.util.HashMap;
import java.util.Map;

/**
 * Link to an Entity with ID.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class EntityLink<T extends ApiIdObject> extends WDiv {

	private final CardType card;

	private final WLibLink link = new WLibLink();

	public EntityLink(final CardType card) {
		this.card = card;
		add(link);
		link.setVisible(false);
		setBeanProperty(".");
	}

	public WLibLink getLink() {
		return link;
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
		if (bean == null) {
			// Could put a message no link
			return;
		}
		// Build url to entity
		String path = EnvironmentHelper.prefixSecureServletPath(card.getPath());
		Map<String, String> params = new HashMap<>(1);
		params.put("id", bean.getId());
		String url = WebUtilities.getPath(path, params);
		// Setup link (base url flag)
		link.setUrl(url, true);
		link.setText(getLabelText(bean));
		link.setOpenNewWindow(false);
		link.setVisible(true);
	}

	protected String getLabelText(final T bean) {
		if (bean instanceof ApiKeyIdObject) {
			return bean.getDescription() + " [" + ((ApiKeyIdObject) bean).getBusinessKey() + "]";
		}
		return bean.getDescription();
	}

}
