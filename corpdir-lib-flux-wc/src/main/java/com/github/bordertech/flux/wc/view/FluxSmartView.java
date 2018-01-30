package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.view.SmartView;
import java.util.List;

/**
 * Smart View.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface FluxSmartView<T> extends FluxDumbView<T>, SmartView<T> {

	/**
	 * Child views.
	 *
	 * @return the list of child views
	 */
	@Override
	List<? extends FluxDumbView> getViews();

	/**
	 *
	 * @param viewId the view id to return
	 * @return the view for the view id
	 */
	@Override
	FluxDumbView getView(final String viewId);

	void configAjax(final FluxDumbView view);

	/**
	 * @return true if this view is an AJAX context
	 */
	boolean isAjaxContext();

	/**
	 *
	 * @param ajaxContext true if this view is an AJAX context
	 */
	void setAjaxContext(final boolean ajaxContext);

}
