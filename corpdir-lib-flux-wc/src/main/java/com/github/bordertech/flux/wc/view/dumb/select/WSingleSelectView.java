package com.github.bordertech.flux.wc.view.dumb.select;

import com.github.bordertech.flux.wc.common.AppAjaxControl;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WSingleSelect;
import com.github.bordertech.wcomponents.WText;

/**
 * Select single view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WSingleSelectView<T> extends AbstractListSingleSelectView<T> {

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
			return !isViewMode();
		}
	};

	private final WList list = new WList(WList.Type.STACKED) {
		@Override
		public boolean isVisible() {
			return isViewMode();
		}

	};

	private final AppAjaxControl ajax = new AppAjaxControl(select);

	public WSingleSelectView(final String viewId) {
		super(viewId);
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
		registerSelectUnselectAjaxControl(ajax);

	}

	@Override
	protected void initViewContent(final Request request) {
		// AJAX
		addEventAjaxTarget(this);
		// Set options on the select
		select.setOptions(getViewBean());
		super.initViewContent(request);
	}

	public final WList getList() {
		return list;
	}

	public final WSingleSelect getSelect() {
		return select;
	}

}
