package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Message events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum MsgEventType implements EventType {
	SUCCESS,
	WARN,
	ERROR,
	INFO,
	VALIDATION,
	RESET
}
