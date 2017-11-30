package com.github.bordertech.flux.wc.view.event.base;

import com.github.bordertech.flux.wc.view.event.MessageEventType;

/**
 * Message view base events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum MessageBaseEventType implements MessageEventType {
	SUCCESS,
	WARN,
	ERROR,
	INFO,
	VALIDATION,
	RESET
}
