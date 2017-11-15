package com.github.bordertech.flux.wc.app.view.event.base;

import com.github.bordertech.flux.wc.app.view.event.MessageViewEvent;

/**
 * Message view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum MessageBaseViewEvent implements MessageViewEvent {
	SUCCESS,
	WARN,
	ERROR,
	INFO,
	VALIDATION,
	RESET
}
