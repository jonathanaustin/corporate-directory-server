package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.web.ui.model.AbstractBeanModel;
import java.util.UUID;

/**
 * Contact model.
 *
 * @author jonathan
 */
public class ChannelModel extends AbstractBeanModel<Channel> {

	@Override
	public Channel createInstance() {
		return new Channel(UUID.randomUUID().toString());
	}

}
