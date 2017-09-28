package com.github.bordertech.corpdir.web.ui.panel;

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
	 */
	public ChannelPanel() {
		WDropdown dropType = new WDropdown(ChannelTypeEnum.values());
		addInputField(dropType, "Channel type", "type", true);
		addTextField("Value", "value", false);
	}

}
