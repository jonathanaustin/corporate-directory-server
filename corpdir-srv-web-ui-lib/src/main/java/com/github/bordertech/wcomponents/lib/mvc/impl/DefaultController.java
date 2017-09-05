package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.Matcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultEvent;
import com.github.bordertech.wcomponents.lib.flux.impl.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.wc.DispatcherHelper;
import com.github.bordertech.wcomponents.lib.model.Model;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEvent;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractWComponent implements Controller {

	public DefaultController() {
		this(null);
	}

	public DefaultController(final String qualifier) {
		setQualifier(qualifier);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DispatcherHelper.getInstance();
	}

	@Override
	public final String getQualifier() {
		return getComponentModel().qualifier;
	}

	@Override
	public final void setQualifier(final String qualifier) {
		getOrCreateComponentModel().qualifier = qualifier;
	}

	@Override
	public void checkConfig() {
	}

	@Override
	public void reset() {
		resetViews();
		super.reset();
	}

	@Override
	public List<View> getViews() {
		CtrlModel model = getComponentModel();
		return model.views == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(model.views);
	}

	@Override
	public void addView(final View view) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.views == null) {
			model.views = new ArrayList<>();
		}
		model.views.add(view);
	}

	@Override
	public void resetViews() {
		for (View view : getViews()) {
			view.resetView();
		}
	}

	@Override
	public void setupListeners() {
	}

	/**
	 * A helper method to register a listener with an Event Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 */
	protected final void registerListener(final Listener listener, final EventType eventType) {
		String qualifier = getListenerQualifier(eventType);
		registerListener(listener, new EventMatcher(eventType, qualifier));
	}

	protected void registerListener(final Listener listener, final Matcher matcher) {
		String id = getDispatcher().register(listener, matcher);
		findParentCombo().registerListenerId(id);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	protected final void dispatchCtrlEvent(final EventType eventType) {
		dispatchCtrlEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	protected void dispatchCtrlEvent(final EventType eventType, final Object data) {
		dispatchCtrlEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	protected void dispatchCtrlEvent(final EventType eventType, final Object data, final Exception exception) {
		String qualifier = getDispatcherQualifier(eventType);
		DefaultEvent event = new DefaultEvent(new EventQualifier(eventType, qualifier), data, exception);
		getDispatcher().dispatch(event);
	}

	protected void dispatchMessageReset() {
		dispatchMessage(MsgEventType.RESET, "");
	}

	protected void dispatchValidationMessages(final List<Diagnostic> diags) {
		String qualifier = getDispatcherQualifier(MsgEventType.VALIDATION);
		dispatchMessageEvent(new MsgEvent(diags, qualifier));
	}

	protected void dispatchMessage(final MsgEventType type, final String text) {
		String qualifier = getDispatcherQualifier(type);
		dispatchMessageEvent(new MsgEvent(type, qualifier, text));
	}

	protected void dispatchMessage(final MsgEventType type, final List<String> texts) {
		String qualifier = getDispatcherQualifier(type);
		dispatchMessageEvent(new MsgEvent(type, qualifier, texts, true));
	}

	protected void dispatchMessageEvent(final MsgEvent event) {
		getDispatcher().dispatch(event);
	}

	protected String getDispatcherQualifier(final EventType type) {
		String qualifier = getDispatcherOverride(type);
		return qualifier == null ? getQualifier() : qualifier;
	}

	@Override
	public void addDispatcherOverride(final String qualifier, final EventType... types) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.dispatcherOverride == null) {
			model.dispatcherOverride = new HashMap<>();
		}
		for (EventType type : types) {
			model.dispatcherOverride.put(type, qualifier);
		}
	}

	protected String getDispatcherOverride(final EventType type) {
		CtrlModel model = getComponentModel();
		if (model.dispatcherOverride != null) {
			return model.dispatcherOverride.get(type);
		}
		return null;
	}

	protected String getListenerQualifier(final EventType type) {
		String qualifier = getListenerOverride(type);
		return qualifier == null ? getQualifier() : qualifier;
	}

	@Override
	public void addListenerOverride(final String qualifier, final EventType... types) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.listenerOverride == null) {
			model.listenerOverride = new HashMap<>();
		}
		for (EventType type : types) {
			model.listenerOverride.put(type, qualifier);
		}
	}

	protected String getListenerOverride(final EventType type) {
		CtrlModel model = getComponentModel();
		if (model.listenerOverride != null) {
			return model.listenerOverride.get(type);
		}
		return null;
	}

	protected Model getModel(final Class clazz) {
		// Get Parent Combo
		ComboView combo = findParentCombo();
		return combo == null ? null : combo.getModel(clazz);
	}

	protected ComboView findParentCombo() {
		return WebUtilities.getAncestorOfClass(ComboView.class, this);
	}

	@Override
	protected CtrlModel newComponentModel() {
		return new CtrlModel();
	}

	@Override
	protected CtrlModel getComponentModel() {
		return (CtrlModel) super.getComponentModel();
	}

	@Override
	protected CtrlModel getOrCreateComponentModel() {
		return (CtrlModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class CtrlModel extends ComponentModel {

		private List<View> views;

		private String qualifier;

		private Map<EventType, String> dispatcherOverride;
		private Map<EventType, String> listenerOverride;

	}

}
