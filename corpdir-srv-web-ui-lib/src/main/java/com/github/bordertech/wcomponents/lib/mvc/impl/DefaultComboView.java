package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.model.Model;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultComboView extends TemplateView implements ComboView {

	public DefaultComboView(final String templateName, final Dispatcher dispatcher) {
		this(templateName, dispatcher, null);
	}

	public DefaultComboView(final String templateName, final Dispatcher dispatcher, final String qualifier) {
		super(templateName, dispatcher, qualifier);
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public void configViews() {
		checkConfig();
		// Call Config on any COMBO Views
		for (View view : getViews()) {
			if (view instanceof ComboView) {
				((ComboView) view).configViews();
			}
		}
		setConfigured();
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
	public boolean isBlocking() {
		return getComponentModel().block;
	}

	@Override
	public void setBlocking(final boolean block) {
		getOrCreateComponentModel().block = block;
	}

	@Override
	public void addModel(final Model model) {
		ComboModel ctrl = getOrCreateComponentModel();
		if (ctrl.models == null) {
			ctrl.models = new ArrayList<>();
		}
		ctrl.models.add(model);
	}

	@Override
	public Model getModel(final Class clazz) {
		for (Model model : getModels()) {
			if (clazz.isAssignableFrom(model.getClass())) {
				return model;
			}
		}
		// Not found, so try the parent controller Models
		ComboView parent = findParentCombo();
		return parent == null ? null : parent.getModel(clazz);
	}

	protected void checkConfig() {
		for (Controller ctrl : getControllers()) {
			ctrl.checkConfig();
		}
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

	protected List<Model> getModels() {
		ComboModel ctrl = getComponentModel();
		return ctrl.models == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(ctrl.models);
	}

	@Override
	protected ComboView findParentCombo() {
		if (isBlocking()) {
			return null;
		}
		return super.findParentCombo();
	}

	protected boolean isConfigured() {
		return getComponentModel().configured;
	}

	protected void setConfigured() {
		getOrCreateComponentModel().configured = true;
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

		private boolean configured;

		private List<Model> models;

		private boolean block;
	}

}
