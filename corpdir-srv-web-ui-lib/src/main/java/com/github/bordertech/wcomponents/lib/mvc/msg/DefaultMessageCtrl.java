package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.impl.EventMatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.validation.WValidationErrors;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller for a Message View.
 *
 * @author jonathan
 */
public class DefaultMessageCtrl extends DefaultController implements MessageCtrl {

	public DefaultMessageCtrl() {
		addAllMsgTypes();
	}

	@Override
	public void setupController() {
		super.setupController();
		for (MessageEventType type : MessageEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleMessageEvent((MessageEvent) event);
				}
			};
			registerListener(type, listener);
		}
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getMessageView() == null) {
			throw new IllegalStateException("A message view has not been set.");
		}
	}

	@Override
	public final MessageView getMessageView() {
		return getComponentModel().messageView;
	}

	@Override
	public final void setMessageView(final MessageView messageView) {
		getOrCreateComponentModel().messageView = messageView;
		addView(messageView);
	}

	@Override
	public final void addHandleMsgType(final MessageEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.messageTypes == null) {
			model.messageTypes = new HashSet<>();
		}
		model.messageTypes.add(type);
	}

	@Override
	public final void removeHandleMsgType(final MessageEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.messageTypes != null) {
			model.messageTypes.remove(type);
			if (model.messageTypes.isEmpty()) {
				model.messageTypes = null;
			}
		}
	}

	/**
	 * Clear all event types so message controller is not listening to any message type.
	 */
	@Override
	public final void clearAllMsgTypes() {
		getOrCreateComponentModel().messageTypes = null;
	}

	/**
	 * Add all message event types (this is the default state).
	 */
	@Override
	public final void addAllMsgTypes() {
		for (MessageEventType type : MessageEventType.values()) {
			addHandleMsgType(type);
		}
	}

	/**
	 * Helper method to just listen to validation errors and errors.
	 */
	@Override
	public final void addValidationAndErrorMsgTypes() {
		clearAllMsgTypes();
		addHandleMsgType(MessageEventType.RESET);
		addHandleMsgType(MessageEventType.VALIDATION);
		addHandleMsgType(MessageEventType.ERROR);
	}

	@Override
	public final Set<MessageEventType> getHandleMsgTypes() {
		Set<MessageEventType> types = getComponentModel().messageTypes;
		return types == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(types);
	}

	@Override
	protected void registerListener(final MessageEventType eventType, final Listener listener) {
		super.registerListener(eventType, listener);
		// Extra qualifiers
		for (String qualifier : getMessageListenerQualifiers()) {
			String fullQualifier = deriveMessageFullQualifier(qualifier);
			registerListener(new EventMatcher(eventType, fullQualifier), listener);
		}
	}

	@Override
	public final void addMessageListenerQualifier(final String messageQualifier) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.messageListenerQualifiers == null) {
			model.messageListenerQualifiers = new HashSet<>();
		}
		model.messageListenerQualifiers.add(messageQualifier);
	}

	@Override
	public final void removeMessageListenerQualifier(final String messageQualifier) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.messageListenerQualifiers != null) {
			model.messageListenerQualifiers.remove(messageQualifier);
			if (model.messageListenerQualifiers.isEmpty()) {
				model.messageListenerQualifiers = null;
			}
		}
	}

	@Override
	public Set<String> getMessageListenerQualifiers() {
		Set<String> qualifiers = getComponentModel().messageListenerQualifiers;
		return qualifiers == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(qualifiers);
	}

	protected void handleMessageEvent(final MessageEvent event) {
		MessageEventType type = (MessageEventType) event.getQualifier().getEventType();

		if (!checkProcessEvent(type)) {
			return;
		}

		// Clear old messages
		getMessageView().resetView();

		switch (type) {
			case ERROR:
				handleErrorMessage(event);
				return;
			case SUCCESS:
				handleSuccessMessage(event);
				return;
			case WARN:
				handleWarnMessage(event);
				return;
			case INFO:
				handleInfoMessage(event);
				return;
			case VALIDATION:
				handleValidationMessage(event);
				return;

			case RESET:
				// Alreadt reset
				break;
		}
	}

	protected boolean checkProcessEvent(final MessageEventType type) {
		Set<MessageEventType> types = getHandleMsgTypes();
		return types.contains(type);
	}

	protected void handleErrorMessage(final MessageEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.error(msg, event.isEncode());
		}
	}

	protected void handleSuccessMessage(final MessageEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.success(msg, event.isEncode());
		}
	}

	protected void handleWarnMessage(final MessageEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.warn(msg, event.isEncode());
		}
	}

	protected void handleInfoMessage(final MessageEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.info(msg, event.isEncode());
		}
	}

	protected void handleValidationMessage(final MessageEvent event) {
		WValidationErrors errors = getMessageView().getMessages().getValidationErrors();
		errors.clearErrors();
		errors.setErrors(event.getDiags());
		errors.setFocussed();
	}

	protected void handleResetMessages() {
		getMessageView().getMessages().reset();
	}

	@Override
	protected MsgCtrlModel newComponentModel() {
		return new MsgCtrlModel();
	}

	@Override
	protected MsgCtrlModel getComponentModel() {
		return (MsgCtrlModel) super.getComponentModel();
	}

	@Override
	protected MsgCtrlModel getOrCreateComponentModel() {
		return (MsgCtrlModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class MsgCtrlModel extends CtrlModel {

		private MessageView messageView;

		private Set<MessageEventType> messageTypes;

		private Set<String> messageListenerQualifiers;

	}
}
