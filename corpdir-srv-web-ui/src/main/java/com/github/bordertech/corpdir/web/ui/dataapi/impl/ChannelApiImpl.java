package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.v1.model.Channel;
import java.util.List;
import java.util.UUID;
import com.github.bordertech.corpdir.web.ui.dataapi.ChannelApi;

/**
 * Channel CRUD API implementation.
 *
 * @author jonathan
 */
public class ChannelApiImpl implements ChannelApi {

	@Override
	public Channel createInstance() {
		return new Channel(ApiIdObject.TEMP_NEW_ID_PREFIX + UUID.randomUUID().toString());
	}

	@Override
	public Channel create(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Channel update(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Channel retrieve(String key) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Channel> search(String criteria) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getItemKey(final Channel item) {
		return item.getId();
	}

}
