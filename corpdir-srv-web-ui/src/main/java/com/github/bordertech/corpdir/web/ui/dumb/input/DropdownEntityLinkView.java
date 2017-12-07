package com.github.bordertech.corpdir.web.ui.dumb.input;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.wc.view.dumb.input.impl.DropdownOptionsView;

/**
 * Dropdown option view with links to entity in readonly mode.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class DropdownEntityLinkView<T extends ApiIdObject> extends DropdownOptionsView<T> {

	private final EntityLink link;

	public DropdownEntityLinkView(final String viewId, final CardType card) {
		super(viewId);
		link = new EntityLink(card);
		link.setVisible(false);
		getReadonlyContainer().add(link);
		setUseReadonlyPanel(true);
	}

	@Override
	protected void initReadonlyContainer() {
		// Get selected
		T bean = getSelectedOption();
		if (bean == null) {
			return;
		}
		link.setBean(bean);
		link.setVisible(true);
	}

}
