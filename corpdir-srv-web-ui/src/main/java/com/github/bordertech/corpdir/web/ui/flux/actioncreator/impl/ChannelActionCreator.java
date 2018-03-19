package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.ChannelApi;
import javax.inject.Inject;

/**
 * Channel action creator implementation.
 *
 * @author jonathan
 */
public class ChannelActionCreator extends DefaultCorpCrudActionCreator<Channel, ChannelApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ChannelActionCreator(final ChannelApi api) {
		super(CorpEntityType.CHANNEL, api);
	}
}
