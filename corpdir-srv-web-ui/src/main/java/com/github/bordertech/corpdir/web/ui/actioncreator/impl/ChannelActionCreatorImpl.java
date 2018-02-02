package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.ChannelActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.ChannelApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudActionCreator;
import javax.inject.Inject;

/**
 * Channel action creator implementation.
 *
 * @author jonathan
 */
public class ChannelActionCreatorImpl extends DefaultCorpCrudActionCreator<Channel, ChannelApi> implements ChannelActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ChannelActionCreatorImpl(final ChannelApi api) {
		super(CorpEntityType.CHANNEL, api);
	}
}
