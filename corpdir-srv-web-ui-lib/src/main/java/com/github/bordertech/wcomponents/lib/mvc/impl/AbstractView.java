package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 */
public abstract class AbstractView<T> extends AbstractBaseView implements View<T> {

	public AbstractView() {
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
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		// Do Nothing
	}

	@Override
	public boolean validateView() {

		// Validate content
		List<Diagnostic> diags = new ArrayList<>();
		WComponent content = getContent();
		content.validate(diags);
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
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		setupTemplateParameters();
	}

	protected void setupTemplateParameters() {
		addParameter("vw-id", getId());
		addParameter("vw-class", getHtmlClass());
		addParameter("vw-hidden", isHidden() ? "hidden=\"true\"" : "");
	}

	/**
	 * Helper method to add target to an Ajax Control.
	 *
	 * @param ajax the AJAX control
	 * @param target the AJAX target
	 */
	protected void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final AjaxTarget target) {
		// Make Sure the Targets have not already been added
		List<AjaxTarget> current = ajax.getTargets();
		if (!current.contains(target)) {
			ajax.addTarget(target);
		}
	}

	protected void initViewContent(final Request request) {
		ComboView combo = findParentCombo();
		if (combo != null) {
			combo.configAjax(this);
		}
	}

	@Override
	public boolean isQualifierContext() {
		return getComponentModel().qualifierContext;
	}

	@Override
	public void setQualifierContext(final boolean qualifierContext) {
		getOrCreateComponentModel().qualifierContext = qualifierContext;
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
	public static class ViewModel extends BaseModel {

		private boolean contentVisible = true;

		private boolean qualifierContext;
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
