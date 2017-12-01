package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.ChannelTypeEnum;
import com.github.bordertech.wcomponents.WDropdown;

/**
 * Channel panel.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ChannelPanel extends BasicApiKeyPanel<Channel> {

	/**
	 * Construct basic channel panel.
	 *
	 * @param viewId the viewId
	 */
	public ChannelPanel(final String viewId) {
		super(viewId);
		WDropdown dropType = new WDropdown(ChannelTypeEnum.values());
		addInputField(dropType, "Channel type", "type", true);
		addTextField("Value", "value", false);
	}

}
