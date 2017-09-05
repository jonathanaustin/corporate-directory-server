package com.github.bordertech.wcomponents.lib.mvc.msg;

import java.util.Set;

/**
 *
 * @author jonathan
 */
public interface MessageCtrl {

	/**
	 * Add all message event types (this is the default state).
	 */
	void addAllMsgTypes();

	/**
	 *
	 * @param type the message event type to listen for
	 */
	void addHandleMsgType(final MsgEventType type);

	/**
	 *
	 * @param type the message type to no longer list to
	 */
	void removeHandleMsgType(final MsgEventType type);

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
	Set<MsgEventType> getHandleMsgTypes();

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

	/**
	 *
	 * @param event the event to processed
	 * @return true if processed
	 */
	boolean handleMessageEvent(final MsgEvent event);

}
