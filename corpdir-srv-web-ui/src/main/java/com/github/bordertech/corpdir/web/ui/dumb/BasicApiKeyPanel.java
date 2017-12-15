package com.github.bordertech.corpdir.web.ui.dumb;

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

	/**
	 * Create a new instance of this panel.
	 *
	 * @param viewId the viewId
	 */
	public BasicApiKeyPanel(final String viewId) {
		this(viewId, true);
	}

	/**
	 * Construct basic API Key panel.
	 *
	 * @param viewId the viewId
	 * @param addFields add the default fields
	 */
	public BasicApiKeyPanel(final String viewId, final boolean addFields) {
		super(viewId, false);
		// Form Defaults
		if (addFields) {
			addTextField("Business key", "businessKey", true);
			addTextField("Description", "description", true);
			addCheckBox("Active", "active", false);
		}

		// Version Default
		addVersionItem("Custom", "custom");
	}

}
