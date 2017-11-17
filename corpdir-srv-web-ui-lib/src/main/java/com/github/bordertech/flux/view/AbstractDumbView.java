package com.github.bordertech.flux.view;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.key.EventType;
import com.github.bordertech.flux.wc.app.common.AppAjaxControl;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.event.base.MessageBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.form.FormUpdateable;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract dumb view allows different types of components to hold the view content.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractDumbView<T> extends WTemplate implements DumbView<T> {

	private final String viewId;

	public AbstractDumbView(final String viewId) {
		this(viewId, "wclib/hbs/layout/default-view.hbs");
	}

	public AbstractDumbView(final String viewId, final String templateName) {
		super(templateName, TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		this.viewId = viewId;
		// Set ID name so it is validated
		setIdName(viewId);
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public final String getViewId() {
		return viewId;
	}

	@Override
	public final String getIdName() {
		return viewId;
	}

	@Override
	public void resetContent() {
		getContent().reset();
	}

	@Override
	public boolean isContentVisible() {
		return getComponentModel().contentVisible;
	}

	@Override
	public void setContentVisible(final boolean visible) {
		if (isContentVisible() != visible) {
			getOrCreateComponentModel().contentVisible = visible;
		}
	}

	@Override
	public void resetView() {
		reset();
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !isContentVisible();
	}

	@Override
	public boolean validateView() {

		// Validate content
		List<Diagnostic> diags = new ArrayList<>();
		WComponent content = getContent();
		content.validate(diags);
		customValidation(diags);
		content.showWarningIndicators(diags);
		content.showErrorIndicators(diags);

		// Check if contains errors
		if (containsError(diags)) {
			dispatchValidationMessages(diags);
			return false;
		} else {
			dispatchMessageReset();
			return true;
		}
	}

	@Override
	public void updateViewBean() {
		WebUtilities.updateBeanValue(this);
	}

	@Override
	public T getViewBean() {
		return (T) getBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		setBean(viewBean);
	}

	@Override
	public void addEventAjaxTarget(final AjaxTarget target, final ViewEventType... eventTypes) {
		// Add the targets to the registered AJAX Controls
		Map<ViewEventType, Set<AppAjaxControl>> map = getRegisteredEventAjaxControls();
		if (map.isEmpty()) {
			return;
		}
		if (eventTypes.length == 0) {
			// Add to all registered AJAX Controls
			for (Set<AppAjaxControl> ctrls : map.values()) {
				for (AppAjaxControl ctrl : ctrls) {
					if (!ctrl.getTargets().contains(target)) {
						ctrl.addTarget(target);
					}
				}
			}
		} else {
			// Add to AJAX Control for each event type (if it has been registered)
			for (EventType type : eventTypes) {
				Set<AppAjaxControl> ctrls = map.get(type);
				if (ctrls != null) {
					for (AppAjaxControl ctrl : ctrls) {
						if (!ctrl.getTargets().contains(target)) {
							ctrl.addTarget(target);
						}
					}
				}
			}
		}
	}

	@Override
	public void clearEventAjaxTargets(final ViewEventType type) {
		Map<ViewEventType, Set<AppAjaxControl>> map = getRegisteredEventAjaxControls();
		if (map.isEmpty()) {
			return;
		}
		Set<AppAjaxControl> ctrls = map.get(type);
		if (ctrls != null) {
			for (AppAjaxControl ctrl : ctrls) {
				ctrl.removeAllTargets();
			}
		}
	}

	@Override
	public void registerEventAjaxControl(final ViewEventType type, final AppAjaxControl ajax) {
		ViewModel model = getOrCreateComponentModel();
		if (model.ajaxControls == null) {
			model.ajaxControls = new HashMap<>();
		}
		Set<AppAjaxControl> ctrls = model.ajaxControls.get(type);
		if (ctrls == null) {
			ctrls = new HashSet<>();
			model.ajaxControls.put(type, ctrls);
		}
		ctrls.add(ajax);
	}

	protected boolean isView(final String viewId, final DumbView view) {
		return Objects.equals(viewId, view.getViewId());
	}

	protected boolean isEvent(final ViewEventType type1, final ViewEventType type2) {
		return Objects.equals(type1, type2);
	}

	protected void customValidation(final List<Diagnostic> diags) {
	}

	protected Map<ViewEventType, Set<AppAjaxControl>> getRegisteredEventAjaxControls() {
		ViewModel model = getComponentModel();
		return model.ajaxControls == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(model.ajaxControls);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		setupTemplateParameters();
	}

	protected void setupTemplateParameters() {
		addParameter("vw-id", getId());
		addParameter("vw-class", getHtmlClass());
		addParameter("vw-hidden", isHidden() ? "hidden=\"true\"" : "");
	}

	protected void initViewContent(final Request request) {
		// Configure AJAX
		SmartView view = findParentViewContainer();
		if (view != null) {
			view.configAjax(this);
		}
		// Check for form updateable
		if (this instanceof FormUpdateable) {
			// Get parent form
			FormView form = WebUtilities.getAncestorOfClass(FormView.class, this);
			if (form != null) {
				((FormUpdateable) this).doMakeFormReadonly(form.isFormReadonly());
			}
		}
	}

	protected void dispatchMessageReset() {
		dispatchViewEvent(MessageBaseViewEvent.RESET);
	}

	protected void dispatchValidationMessages(final List<Diagnostic> diags) {
		dispatchViewEvent(MessageBaseViewEvent.RESET, diags);
	}

	protected void dispatchMessageSuccess(final String msg) {
		dispatchMessageSuccess(Arrays.asList(msg));
	}

	protected void dipsatchMessageWarning(final String msg) {
		dispatchMessageWarning(Arrays.asList(msg));
	}

	protected void dispatchMessageError(final String msg) {
		dispatchMessageError(Arrays.asList(msg));
	}

	protected void dispatchMessageInfo(final String msg) {
		dispatchMessageInfo(Arrays.asList(msg));
	}

	protected void dispatchMessageSuccess(final List<String> msgs) {
		dispatchViewEvent(MessageBaseViewEvent.SUCCESS, msgs);
	}

	protected void dispatchMessageWarning(final List<String> msgs) {
		dispatchViewEvent(MessageBaseViewEvent.WARN, msgs);
	}

	protected void dispatchMessageError(final List<String> msgs) {
		dispatchViewEvent(MessageBaseViewEvent.ERROR, msgs);
	}

	protected void dispatchMessageInfo(final List<String> msgs) {
		dispatchViewEvent(MessageBaseViewEvent.INFO, msgs);
	}

	/**
	 * Helper method to dispatch an event for this view to the smart view.
	 *
	 * @param eventType the view event
	 */
	@Override
	public void dispatchViewEvent(final ViewEventType eventType) {
		dispatchViewEvent(eventType, null);
	}

	/**
	 * Helper method to dispatch an event for this view to the smart view.
	 *
	 * @param eventType the view event
	 * @param data the event data
	 */
	@Override
	public void dispatchViewEvent(final ViewEventType eventType, final Object data) {
		SmartView parent = null;
		// Check if this is a smart view and is not in "dumb mode". Dumb mode delegates to the parent.
		if (this instanceof SmartView) {
			SmartView smart = (SmartView) this;
			boolean pass = smart.isDumbMode() && smart.getPassThroughs().contains(eventType);
			if (!pass) {
				parent = (SmartView) this;
			}
		}
		if (parent == null) {
			parent = findParentViewContainer();
		}
		if (parent != null) {
			parent.handleViewEvent(getViewId(), eventType, data);
		}
	}

	protected SmartView findParentViewContainer() {
		return ViewUtil.findParentSmartView(this);
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ViewModel extends TemplateModel {

		private boolean contentVisible = true;

		private Map<ViewEventType, Set<AppAjaxControl>> ajaxControls;

	}

	/**
	 * Borrowed from ValidatinAction (Should be public). Indicates whether the given list of diagnostics contains any
	 * errors.
	 *
	 * @param diags the list into which any validation diagnostics were added.
	 * @return true if any of the diagnostics in the list are errors, false otherwise.
	 */
	private static boolean containsError(final List<Diagnostic> diags) {
		if (diags == null || diags.isEmpty()) {
			return false;
		}

		for (Diagnostic diag : diags) {
			if (diag.getSeverity() == Diagnostic.ERROR) {
				return true;
			}
		}

		return false;
	}

}
