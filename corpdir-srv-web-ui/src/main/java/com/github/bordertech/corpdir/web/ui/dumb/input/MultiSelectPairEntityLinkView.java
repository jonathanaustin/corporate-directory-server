package com.github.bordertech.corpdir.web.ui.dumb.input;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.wc.view.dumb.input.impl.MultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.WList;
import java.util.List;

/**
 * MultiSelect option view with links to entity in readonly mode.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class MultiSelectPairEntityLinkView<T extends ApiIdObject> extends MultiSelectPairOptionsView<T> {

	private final WList links = new WList(WList.Type.STACKED);

	public MultiSelectPairEntityLinkView(final String viewId, final CardType card) {
		super(viewId);
		links.setRepeatedComponent(new EntityLink(card));
		links.setVisible(false);
		getReadonlyContainer().add(links);
		setUseReadonlyPanel(true);
	}

	@Override
	protected void initReadonlyContainer() {
		// Get selected
		List<T> beans = getSelectedOptions();
		if (beans == null || beans.isEmpty()) {
			return;
		}
		links.setBeanList(beans);
		links.setVisible(true);
	}

}
