package com.github.bordertech.corpdir.web.ui.common;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;

/**
 * Basic API Key detail Form View.
 *
 * @param <T> the API object type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicApiKeyPanel<T extends ApiKeyIdObject> extends BasicApiIdPanel<T> {

	public BasicApiKeyPanel() {
		this(true);
	}

	/**
	 * Construct basic detail panel.
	 *
	 * @param addFields add the default fields
	 */
	public BasicApiKeyPanel(final boolean addFields) {
		super(addFields);
		// Form Defaults
		if (addFields) {
			addTextField("Business key", "businessKey", true);
			addCheckBox("Active", "active", false);
		}

		// Version Defaul
		addVersionItem("Custom", "custom");
	}

}
