package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.Matcher;
import com.github.bordertech.wcomponents.lib.flux.impl.EventMatcher;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.Model;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractBaseMvc implements Controller {

	public DefaultController() {
		super(null);
	}

	@Override
	public void checkConfig() {
	}

	@Override
	public void reset() {
		unregisterListenerIds();
		for (View view : getViews()) {
			view.reset();
		}
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
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			// This should normally be called as configView is called by itsparent ComboView
			setupController();
		}
	}

	@Override
	public void setupController() {
		setInitialised(true);
	}

	protected Model getModel(final String key) {
		Model model = ModelProviderFactory.getInstance().getModel(key);
		return model;
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
		registerListenerId(id);
	}

	protected String getListenerQualifier(final EventType type) {
		String qualifier = getListenerOverride(type);
		return qualifier == null ? getFullQualifier() : qualifier;
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
		if (hasRegisteredListenerIds()) {
			// Register the listeners again to pick up override
			unregisterListenerIds();
			setupController();
		}

	}

	protected String getListenerOverride(final EventType type) {
		CtrlModel model = getComponentModel();
		if (model.listenerOverride != null) {
			return model.listenerOverride.get(type);
		}
		return null;
	}

	protected void registerListenerId(final String id) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.registeredIds == null) {
			model.registeredIds = new HashSet<>();
		}
		model.registeredIds.add(id);
	}

	protected void unregisterListenerIds() {
		CtrlModel model = getOrCreateComponentModel();
		if (model.registeredIds != null) {
			Dispatcher dispatcher = getDispatcher();
			for (String id : model.registeredIds) {
				dispatcher.unregister(id);
			}
			model.registeredIds = null;
		}
	}

	protected boolean hasRegisteredListenerIds() {
		CtrlModel model = getComponentModel();
		return model.registeredIds != null && !model.registeredIds.isEmpty();
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
	public static class CtrlModel extends BaseModel {

		private List<View> views;
		private Map<EventType, String> listenerOverride;
		private Set<String> registeredIds;

	}

}
