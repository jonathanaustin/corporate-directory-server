package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.EventType;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractAppView<T> extends AbstractView implements AppView<T> {

	public AbstractAppView() {
		super("wclib/hbs/layout/default-view.hbs");
		setSearchAncestors(false);
		setBeanProperty(".");
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
			handleValidationMessages(diags);
			return false;
		} else {
			handleMessageReset();
			return true;
		}
	}

	protected void customValidation(final List<Diagnostic> diags) {
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
	public void addEventAjaxTarget(final AjaxTarget target, final EventType... eventTypes) {
		// Add the targets to the registered AJAX Controls
		Map<EventType, Set<AppAjaxControl>> map = getRegisteredEventAjaxControls();
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
	public void clearEventAjaxTargets(final EventType type) {
		Map<EventType, Set<AppAjaxControl>> map = getRegisteredEventAjaxControls();
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
	public void registerEventAjaxControl(final EventType type, final AppAjaxControl ajax) {
		AppViewModel model = getOrCreateComponentModel();
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

	@Override
	public WMessages getViewMessages() {
		return WMessages.getInstance(this);
	}

	protected Map<EventType, Set<AppAjaxControl>> getRegisteredEventAjaxControls() {
		AppViewModel model = getComponentModel();
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
//		// Configure AJAX
//		ComboView combo = findParentCombo();
//		if (combo != null) {
//			combo.configAjax(this);
//		}
//		// Check for updateable
//		if (this instanceof FormUpdateable) {
//			// Get parent form
//			FormView view = WebUtilities.getAncestorOfClass(FormView.class, this);
//			if (view != null) {
//				((FormUpdateable) this).doMakeFormReadonly(view.isFormReadonly());
//			}
//		}
	}

	protected void handleMessageReset() {
		getViewMessages().reset();
	}

	protected void handleValidationMessages(final List<Diagnostic> diags) {
		getViewMessages().getValidationErrors().setErrors(diags);
	}

	protected void handleMessageSuccess(final String text) {
		getViewMessages().success(text);
	}

	protected void handleMessageWarning(final String text) {
		getViewMessages().warn(text);
	}

	protected void handleMessageError(final String text) {
		getViewMessages().error(text);
	}

	protected void handleMessageInfo(final String text) {
		getViewMessages().info(text);
	}

	@Override
	protected AppViewModel newComponentModel() {
		return new AppViewModel();
	}

	@Override
	protected AppViewModel getComponentModel() {
		return (AppViewModel) super.getComponentModel();
	}

	@Override
	protected AppViewModel getOrCreateComponentModel() {
		return (AppViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class AppViewModel extends ViewModel {

		private boolean contentVisible = true;

		private Map<EventType, Set<AppAjaxControl>> ajaxControls;

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
