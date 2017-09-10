package com.github.bordertech.wcomponents.lib.app.list;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WSingleSelect;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Select single view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SelectSingleView<T> extends DefaultSelectView<T> {

	private final WSingleSelect select = new WSingleSelect() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (getOptions() == null || getOptions().isEmpty()) {
				setSelected(null);
				setSelectedItem(null);
			} else {
				setSelected(getSelectedItem());
			}
		}

		@Override
		public boolean isDisabled() {
			return isListModeView();
		}
	};
	private final WDiv ajaxPanel = new WDiv();
	private final WAjaxControl ajax = new WAjaxControl(select);

	public SelectSingleView() {
		getContent().add(select);
		getContent().add(ajaxPanel);
		ajaxPanel.add(ajax);
		select.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				setSelectedItem((T) select.getSelected());
				doDispatchSelectEvent();
			}
		});
		select.setRows(5);
	}

	@Override
	protected void initViewContent(final Request request) {
		// AJAX
		ajax.addTarget(this);
		// Set options on the select
		select.setOptions(getViewBean());
		super.initViewContent(request);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		ajax.addTarget(target);
	}

}
