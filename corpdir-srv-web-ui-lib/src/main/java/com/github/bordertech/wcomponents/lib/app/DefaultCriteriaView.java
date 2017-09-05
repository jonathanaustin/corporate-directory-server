package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultViewBound;

/**
 * Default criteria view.
 *
 * @param <T> the criteria type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultCriteriaView<T> extends DefaultViewBound<T> implements CriteriaView<T> {

	private final WButton searchButton = new WButton("Search");

	private final WAjaxControl ajax = new WAjaxControl(searchButton);

	public DefaultCriteriaView() {
		this(null);
	}

	public DefaultCriteriaView(final String qualifier) {
		super(qualifier);
		searchButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				if (validateView()) {
					doSearchButtonAction();
				}
			}
		});

		getContent().add(ajax);
	}

	public final WButton getSearchButton() {
		return searchButton;
	}

	/**
	 * The search button action.
	 */
	protected void doSearchButtonAction() {
		updateViewBean();
		doDispatchSearchEvent();
	}

	/**
	 * Dispatch the search event.
	 */
	protected void doDispatchSearchEvent() {
		T criteria = getViewBean();
		dispatchViewEvent(ActionEventType.SEARCH, criteria);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		addEventTargetsToAjaxCtrl(ajax, target);
	}

}
