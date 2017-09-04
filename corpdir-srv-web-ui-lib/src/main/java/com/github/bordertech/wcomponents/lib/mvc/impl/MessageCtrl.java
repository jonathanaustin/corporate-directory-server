package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.MsgEvent;
import com.github.bordertech.wcomponents.lib.mvc.MsgEventType;
import com.github.bordertech.wcomponents.lib.mvc.MsgView;
import com.github.bordertech.wcomponents.validation.WValidationErrors;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller for a Message View.
 *
 *
 * @author jonathan
 */
public class MessageCtrl extends DefaultController {

	public MessageCtrl(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public MessageCtrl(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		// Listeners for reset
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleResetEvent();
			}
		};
		registerCtrlListener(listener, ActionEventType.RESET_MSGS);
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getMessageView() == null) {
			throw new IllegalStateException("A message view has not been set.");
		}
	}

	public final MsgView getMessageView() {
		return getComponentModel().messageView;
	}

	public final void setMessageView(final MsgView messageView) {
		getOrCreateComponentModel().messageView = messageView;
		addView(messageView);
	}

	public final void addHandleMsgType(final MsgEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.events == null) {
			model.events = new HashSet<>();
		}
		model.events.add(type);
	}

	public final void removeHandleMsgType(final MsgEventType type) {
		MsgCtrlModel model = getOrCreateComponentModel();
		if (model.events != null) {
			model.events.remove(type);
			if (model.events.isEmpty()) {
				model.events = null;
			}
		}
	}

	public final Set<MsgEventType> getHandleMsgTypes() {
		MsgCtrlModel model = getComponentModel();
		return model.events == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(model.events);
	}

	public boolean handleMessageEvent(final MsgEvent event) {
		MsgEventType type = event.getType();

		// Check if process the events
		if (!checkProcessEvent(type)) {
			return false;
		}

		switch (type) {
			case ERROR:
				handleErrorMessage(event);
				return true;
			case SUCCESS:
				handleSuccessMessage(event);
				return true;
			case WARN:
				handleWarnMessage(event);
				return true;
			case INFO:
				handleInfoMessage(event);
				return true;
			case VALIDATION:
				handleValidationMessage(event);
				return true;
		}
		return false;
	}

	protected boolean checkProcessEvent(final MsgEventType type) {
		Set<MsgEventType> types = getHandleMsgTypes();
		// Empty handle all
		return types.isEmpty() || types.contains(type);
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

		private MsgView messageView;

		private Set<MsgEventType> events;

	}
}
