package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.ViewBound;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import com.github.bordertech.wcomponents.validation.WValidationErrors;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the view bean
 */
public class DefaultViewBound<T> extends DefaultView implements ViewBound<T> {

	public DefaultViewBound(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultViewBound(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public void updateViewBean() {
		WebUtilities.updateBeanValue(this);
	}

	@Override
	public boolean validateView() {
		WValidationErrors errorsBox = getViewMessages().getValidationErrors();
		errorsBox.clearErrors();

		List<Diagnostic> diags = new ArrayList<>();
		WDiv content = getContent();
		content.validate(diags);
		content.showWarningIndicators(diags);
		content.showErrorIndicators(diags);

		if (containsError(diags)) {
			errorsBox.setErrors(diags);
			errorsBox.setFocussed();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public T getViewBean() {
		return (T) getBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		setBean(viewBean);
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
