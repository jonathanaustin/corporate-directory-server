package com.github.bordertech.corpdir.web.ui.common;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.WList;

/**
 * List of entity links.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class EntityLinkRepeater<T extends ApiIdObject> extends WDiv {

	private final WList links = new WList(WList.Type.STACKED);

	public EntityLinkRepeater(final CardType card) {
		links.setRepeatedComponent(new EntityLink(card));
		links.setBeanProperty(".");
		add(links);
	}

	public WList getLinks() {
		return links;
	}

}
