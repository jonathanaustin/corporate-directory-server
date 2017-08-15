package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.app.type.CriteriaEventType;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.validation.ValidatingAction;

/**
 * Default criteria view.
 *
 * @param <T> the criteria type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractCriteriaView<T> extends DefaultView<T> implements CriteriaView<T> {

	private final WButton searchButton = new WButton("Search");

	private final WAjaxControl ajax = new WAjaxControl(searchButton);

	public AbstractCriteriaView(final BasicController ctrl) {
		super(ctrl);
		searchButton.setAction(new ValidatingAction(getViewMessages().getValidationErrors(), getViewHolder()) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				doSearchButtonAction();
			}
		});

		getViewHolder().add(ajax);
	}

	@Override
	public final WButton getSearchButton() {
		return searchButton;
	}

	/**
	 * The search button action.
	 */
	protected void doSearchButtonAction() {
		doUpdateViewBean();
		doDispatchSearchEvent();
	}

	/**
	 * Dispatch the search event.
	 */
	protected void doDispatchSearchEvent() {
		T criteria = getViewBean();
		dispatchViewEvent(CriteriaEventType.SEARCH, criteria);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		addEventTargetsToAjaxCtrl(ajax, target);
	}

}
