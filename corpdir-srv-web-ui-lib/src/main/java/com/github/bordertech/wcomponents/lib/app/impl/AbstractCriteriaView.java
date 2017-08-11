package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.event.CriteriaEvent;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
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
public abstract class AbstractCriteriaView<T> extends DefaultView implements CriteriaView<T> {

	private final WButton searchButton = new WButton("Search");

	private final WAjaxControl ajax = new WAjaxControl(searchButton);

	public AbstractCriteriaView(final Dispatcher dispatcher) {
		super(dispatcher);
		searchButton.setAction(new ValidatingAction(WMessages.getInstance(this).getValidationErrors(), getHolder()) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				doDispatchSearchEvent();
			}
		});

		getHolder().add(ajax);
		ajax.addTarget(getHolder());

	}

	@Override
	public final WButton getSearchButton() {
		return searchButton;
	}

	protected void doDispatchSearchEvent() {
		T criteria = setupCriteria();
		Event event = new Event(this, CriteriaEvent.SEARCH, criteria);
		getDispatcher().dispatch(event);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		ajax.addTarget(target);
	}

	protected abstract T setupCriteria();

}
