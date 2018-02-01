package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * Channel has no backing service. The interface is only used for a new instance.
 *
 * @author jonathan
 */
public interface ChannelApi extends CrudApi<String, String, Channel> {
}
