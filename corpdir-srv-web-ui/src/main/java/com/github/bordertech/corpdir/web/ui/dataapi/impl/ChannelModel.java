package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import java.util.UUID;

/**
 *
 * @author jonathan
 */
public class ChannelModel implements CrudApi<Channel> {

	@Override
	public Channel retrieve(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Channel create(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Channel update(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void delete(Channel entity) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Channel createInstance() {
		return new Channel(ApiIdObject.TEMP_NEW_ID_PREFIX + UUID.randomUUID().toString());
	}

}
