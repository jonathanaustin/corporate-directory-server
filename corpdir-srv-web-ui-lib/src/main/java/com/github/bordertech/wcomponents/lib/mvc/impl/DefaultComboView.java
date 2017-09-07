package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.model.Model;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
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
public class DefaultComboView<T> extends TemplateView<T> implements ComboView<T> {

	public DefaultComboView(final String templateName) {
		this(templateName, null);
	}

	public DefaultComboView(final String templateName, final String qualifier) {
		super(templateName, qualifier);
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			configViews();
		}
	}

	@Override
	public void reset() {
		unregisterListenerIds();
		super.reset();
	}

	@Override
	public void resetView() {
		super.resetView();
		configViews();
	}

	@Override
	public void configViews() {
		checkCtrlConfigs();
		// Call Config on any COMBO Views
		for (View view : getViews()) {
			if (view instanceof ComboView) {
				((ComboView) view).configViews();
			}
		}
		setInitialised(true);
	}

	@Override
	public void configAjax(final View view) {
		// By default ADD all the views as AJAX
		for (View vw : getViews()) {
			if (vw != view) {
				view.addEventTarget(vw);
			}
		}
		// Get the parent controller AJAX Targets
		ComboView parent = findParentCombo();
		if (parent != null) {
			parent.configAjax(view);
		}
	}

	@Override
	public void addDispatcherOverride(final String qualifier, final EventType... types) {
		// Add to child views.
		for (View view : getViews()) {
			view.addDispatcherOverride(qualifier, types);
		}
		// Add to child controllers
		for (Controller ctrl : getControllers()) {
			ctrl.addDispatcherOverride(qualifier, types);
		}
	}

	@Override
	public void addListenerOverride(final String qualifier, final EventType... types) {
		// Add to child controllers
		for (Controller ctrl : getControllers()) {
			ctrl.addListenerOverride(qualifier, types);
		}
	}

	protected void checkCtrlConfigs() {
		for (Controller ctrl : getControllers()) {
			ctrl.checkConfig();
			ctrl.setupListeners();
		}
	}

	@Override
	public boolean isBlocking() {
		return getComponentModel().block;
	}

	@Override
	public void setBlocking(final boolean block) {
		getOrCreateComponentModel().block = block;
	}

	@Override
	public void addModel(final String key, final Model model) {
		ComboModel ctrl = getOrCreateComponentModel();
		if (ctrl.models == null) {
			ctrl.models = new HashMap<>();
		}
		ctrl.models.put(key, model);
	}

	@Override
	public Model getModel(final String key) {
		ComboModel ctrl = getComponentModel();
		if (ctrl.models != null) {
			Model model = ctrl.models.get(key);
			if (model != null) {
				return model;
			}
		}
		// Not found, so try the parent controller Models
		ComboView parent = findParentCombo();
		return parent == null ? null : parent.getModel(key);
	}

	protected List<View> getViews() {
		List<View> views = new ArrayList<>();
		for (WComponent child : getContent().getTaggedComponents().values()) {
			if (child instanceof View) {
				views.add((View) child);
			}
		}
		return Collections.unmodifiableList(views);
	}

	protected List<Controller> getControllers() {
		List<Controller> ctrls = new ArrayList<>();
		for (WComponent child : getContent().getTaggedComponents().values()) {
			if (child instanceof Controller) {
				ctrls.add((Controller) child);
			}
		}
		return Collections.unmodifiableList(ctrls);
	}

	@Override
	protected ComboView findParentCombo() {
		if (isBlocking()) {
			return null;
		}
		return super.findParentCombo();
	}

	@Override
	public void registerListenerId(final String id) {
		ComboModel model = getOrCreateComponentModel();
		if (model.registeredIds == null) {
			model.registeredIds = new HashSet<>();
		}
		model.registeredIds.add(id);
	}

	protected void unregisterListenerIds() {
		ComboModel model = getOrCreateComponentModel();
		if (model.registeredIds != null) {
			Dispatcher dispatcher = getDispatcher();
			for (String id : model.registeredIds) {
				dispatcher.unregister(id);
			}
			model.registeredIds = null;
		}
	}

	@Override
	protected ComboModel newComponentModel() {
		return new ComboModel();
	}

	@Override
	protected ComboModel getComponentModel() {
		return (ComboModel) super.getComponentModel();
	}

	@Override
	protected ComboModel getOrCreateComponentModel() {
		return (ComboModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ComboModel extends ViewModel {

		private Map<String, Model> models;

		private boolean block;

		private Set<String> registeredIds;
	}

}
