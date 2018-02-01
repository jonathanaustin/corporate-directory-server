package com.github.bordertech.corpdir.web.ui.common;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.lib.common.WDiv;

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
