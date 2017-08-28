package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WMessages;

/**
 *
 * @author jonathan
 */
public class ViewMessages extends WMessages {

	public ViewMessages() {
		this(true);
	}
	
	public ViewMessages(final boolean persistent) {
		super(persistent);
		addHtmlClass("wc-margin-s-lg");
	}

}
