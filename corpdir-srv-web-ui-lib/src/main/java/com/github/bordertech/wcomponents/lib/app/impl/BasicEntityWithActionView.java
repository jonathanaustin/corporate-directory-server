package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;

/**
 *
 * @author jonathan
 */
public abstract class BasicEntityWithActionView<T> extends DefaultView<T> implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final EntityView<T> entityView;

	public BasicEntityWithActionView(final BasicController ctrl, final EntityView<T> entityView, final EntityActionView actionView) {
		super(ctrl);
		this.entityView = entityView;
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
