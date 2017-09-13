package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultComboView<T> extends TemplateView<T> implements ComboView<T> {

	public DefaultComboView() {
		super("wclib/hbs/layout/default-view.hbs");
	}

	public DefaultComboView(final String templateName) {
		super(templateName);
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
				view.addEventAjaxTarget(vw);
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
			ctrl.setupController();
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

		private boolean block;

	}

}
