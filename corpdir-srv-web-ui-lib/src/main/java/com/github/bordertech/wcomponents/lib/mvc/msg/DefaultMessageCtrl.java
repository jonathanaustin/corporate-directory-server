package com.github.bordertech.wcomponents.lib.mvc.msg;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
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
		this(null);
	}

	public DefaultMessageCtrl(final String qualifier) {
		super(qualifier);
		addAllMsgTypes();
	}

	@Override
	public void setupListeners() {
		super.setupListeners();
		for (MsgEventType type : MsgEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleMessageEvent((MsgEvent) event);
				}
			};
			registerListener(listener, type);
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
	public final void addHandleMsgType(final MsgEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.events == null) {
			model.events = new HashSet<>();
		}
		model.events.add(type);
	}

	@Override
	public final void removeHandleMsgType(final MsgEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.events != null) {
			model.events.remove(type);
			if (model.events.isEmpty()) {
				model.events = null;
			}
		}
	}

	/**
	 * Clear all event types so message controller is not listening to any message type.
	 */
	@Override
	public final void clearAllMsgTypes() {
		getOrCreateComponentModel().events = null;
	}

	/**
	 * Add all message event types (this is the default state).
	 */
	@Override
	public final void addAllMsgTypes() {
		for (MsgEventType type : MsgEventType.values()) {
			addHandleMsgType(type);
		}
	}

	/**
	 * Helper method to just listen to validation errors and errors.
	 */
	@Override
	public final void addValidationAndErrorMsgTypes() {
		clearAllMsgTypes();
		addHandleMsgType(MsgEventType.VALIDATION);
		addHandleMsgType(MsgEventType.ERROR);
	}

	@Override
	public final Set<MsgEventType> getHandleMsgTypes() {
		MsgCtrlModel model = getComponentModel();
		return model.events == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(model.events);
	}

	public void handleMessageEvent(final MsgEvent event) {
		MsgEventType type = (MsgEventType) event.getQualifier().getEventType();

		if (!checkProcessEvent(type)) {
			return;
		}

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
		}
	}

	protected boolean checkProcessEvent(final MsgEventType type) {
		Set<MsgEventType> types = getHandleMsgTypes();
		return types.contains(type);
	}

	protected void handleErrorMessage(final MsgEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.error(msg, event.isEncode());
		}
	}

	protected void handleSuccessMessage(final MsgEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.success(msg, event.isEncode());
		}
	}

	protected void handleWarnMessage(final MsgEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.warn(msg, event.isEncode());
		}
	}

	protected void handleInfoMessage(final MsgEvent event) {
		WMessages wmsg = getMessageView().getMessages();
		for (String msg : event.getMessages()) {
			wmsg.info(msg, event.isEncode());
		}
	}

	protected void handleValidationMessage(final MsgEvent event) {
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

		private Set<MsgEventType> events;

	}
}
