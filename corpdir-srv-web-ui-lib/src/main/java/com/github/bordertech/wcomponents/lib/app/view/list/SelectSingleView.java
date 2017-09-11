package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WSingleSelect;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Select single view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SelectSingleView<T> extends AbstractSelectView<T> {

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
		public boolean isVisible() {
			return !isListModeView();
		}
	};

	private final WList list = new WList(WList.Type.STACKED) {
		@Override
		public boolean isVisible() {
			return isListModeView();
		}

	};

	private final WAjaxControl ajax = new AppAjaxControl(select);

	public SelectSingleView() {
		// READONLY Version
		list.setRepeatedComponent(new WText());
		list.setSeparator(WList.Separator.DOT);
		list.setBeanProperty(".");

		getContent().add(select);
		getContent().add(list);
		getContent().add(ajax);
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
		addEventTarget(this);
		// Set options on the select
		select.setOptions(getViewBean());
		super.initViewContent(request);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		if (!ajax.getTargets().contains(target)) {
			ajax.addTarget(target);
		}
	}

	public final WList getList() {
		return list;
	}

	public final WSingleSelect getSelect() {
		return select;
	}

}
