package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.v1.model.Channel;
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
