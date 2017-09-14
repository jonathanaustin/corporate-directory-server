package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.lib.mvc.Controller;
import java.util.Set;

/**
 *
 * @author jonathan
 */
public interface MessageCtrl extends Controller {

	/**
	 * Add all message event types (this is the default state).
	 */
	void addAllMsgTypes();

	/**
	 *
	 * @param type the message event type to listen for
	 */
	void addHandleMsgType(final MessageEventType type);

	/**
	 *
	 * @param type the message type to no longer list to
	 */
	void removeHandleMsgType(final MessageEventType type);

	/**
	 * Helper method to just listen to validation errors and errors.
	 */
	void addValidationAndErrorMsgTypes();

	/**
	 * Clear all event types so message controller is not listening to any message type.
	 */
	void clearAllMsgTypes();

	/**
	 *
	 * @return the set of message event types being listened to.
	 */
	Set<MessageEventType> getHandleMsgTypes();

	/**
	 *
	 * @return the message view being controlled
	 */
	MessageView getMessageView();

	/**
	 *
	 * @param messageView the message view being controlled
	 */
	void setMessageView(final MessageView messageView);

	void addMessageListenerQualifier(final String messageQualifier);

	void removeMessageListenerQualifier(final String messageQualifier);

	Set<String> getMessageListenerQualifiers();

}
