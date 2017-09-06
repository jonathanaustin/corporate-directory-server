package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.ChannelModel;
import com.github.bordertech.corpdir.web.ui.panel.ChannelPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudNoSearchView;

/**
 *
 * @author jonathan
 */
public class ChannelCrudView extends DefaultCrudNoSearchView {

	private static final ChannelModel MODEL = new ChannelModel();

	public ChannelCrudView(final String qualifier) {
		super("Channel", MODEL, new ChannelPanel(), qualifier);
		getCrudView().setBeanProperty("channels");
		getCrudView().setSearchAncestors(true);
	}

}
