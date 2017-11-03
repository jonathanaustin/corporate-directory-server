package com.github.bordertech.wcomponents.lib.app.view.event.base;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.Collection;
import java.util.List;

/**
 * Process message events.
 *
 * @author jonathan
 */
public class MessageBaseEventUtil {

	private MessageBaseEventUtil() {
	}

	public static void handleMessageEvent(final WMessages messages, final MessageBaseViewEvent event, final Object data) {

		// Clear old messages
		messages.reset();

		switch (event) {
			case SUCCESS:
				handleMessageSuccess(messages, data);
				break;
			case ERROR:
				handleMessageError(messages, data);
				break;
			case WARN:
				handleMessageWarning(messages, data);
				break;
			case INFO:
				handleMessageInfo(messages, data);
				break;
			case VALIDATION:
				handleValidationMessages(messages, (List<Diagnostic>) data);
				break;

			case RESET:
				// Already reset
				break;
		}
	}

	private static void handleValidationMessages(final WMessages messages, final List<Diagnostic> diags) {
		messages.getValidationErrors().setErrors(diags);
	}

	private static void handleMessageSuccess(final WMessages messages, final Object data) {
		if (data instanceof Collection) {
			for (Object msg : (Collection) data) {
				if (msg != null) {
					messages.success(msg.toString());
				}
			}
		} else if (data != null) {
			messages.success(data.toString());
		}
	}

	private static void handleMessageWarning(final WMessages messages, final Object data) {
		if (data instanceof Collection) {
			for (Object msg : (Collection) data) {
				if (msg != null) {
					messages.warn(msg.toString());
				}
			}
		} else if (data != null) {
			messages.warn(data.toString());
		}
	}

	private static void handleMessageError(final WMessages messages, final Object data) {
		if (data instanceof Collection) {
			for (Object msg : (Collection) data) {
				if (msg != null) {
					messages.error(msg.toString());
				}
			}
		} else if (data != null) {
			messages.error(data.toString());
		}
	}

	private static void handleMessageInfo(final WMessages messages, final Object data) {
		if (data instanceof Collection) {
			for (Object msg : (Collection) data) {
				if (msg != null) {
					messages.info(msg.toString());
				}
			}
		} else if (data != null) {
			messages.info(data.toString());
		}
	}

}
