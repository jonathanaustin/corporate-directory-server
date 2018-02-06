package com.github.bordertech.corpdir.web.ui.actioncreator;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.web.ui.dataapi.ChannelApi;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.CorpCrudActionCreator;

/**
 * Channel CRUD ActionCreator.
 * <p>
 * Channel API has no backing service. The interface is only used for a new instance.
 * </p>
 *
 * @author jonathan
 */
public interface ChannelActionCreator extends CorpCrudActionCreator<Channel, ChannelApi> {
}
