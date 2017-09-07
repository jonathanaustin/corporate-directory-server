package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.wcomponents.WDefinitionList;
import com.github.bordertech.wcomponents.WHorizontalRule;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;

/**
 * Basic API with ID Form.
 *
 * @param <T> the API object type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicApiIdPanel<T extends ApiIdObject> extends BasicApiPanel<T> {

	private final WPanel versionPanel = new WPanel() {
		@Override
		public boolean isVisible() {
			return isExistingEntity();
		}
	};

	private final WDefinitionList versionDef = new WDefinitionList(WDefinitionList.Type.COLUMN);

	/**
	 * Construct basic detail panel. \
	 */
	public BasicApiIdPanel(final String qualifier) {
		this(qualifier, true);
	}

	/**
	 * Construct basic detail panel.
	 *
	 * @param addFields add the default fields
	 */
	public BasicApiIdPanel(final String qualifier, final boolean addFields) {
		super(qualifier);
		getContent().add(versionPanel);
		versionPanel.add(new WHorizontalRule());
		versionPanel.add(versionDef);

		// Form Defaults
		if (addFields) {
			addTextField("Description", "description", true);
		}

		// Version Defaults
		addVersionItem("Version", "timestamp");
		addVersionItem("ID", "id");
	}

	/**
	 * @return the panel that holds the record id and version details
	 */
	protected final WPanel getVersionPanel() {
		return versionPanel;
	}

	protected final WDefinitionList getVersionDef() {
		return versionDef;
	}

	protected final void addVersionItem(final String label, final String beanProperty) {
		WText txtItem = new WText();
		txtItem.setBeanProperty(beanProperty);
		versionDef.addTerm(label, txtItem);

	}

	private boolean isExistingEntity() {
		return getBeanValue() == null ? false : getBeanValue().getId() != null;
	}

}
