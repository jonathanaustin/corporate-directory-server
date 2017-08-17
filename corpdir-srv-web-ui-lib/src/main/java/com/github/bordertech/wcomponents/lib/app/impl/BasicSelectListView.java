package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.app.type.ActionEventType;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.List;

/**
 * Default list view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicSelectListView<T> extends AbstractSelectListView<T> {

	private final WMenu menu = new WMenu(WMenu.MenuType.TREE);

	public BasicSelectListView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public BasicSelectListView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
		getContent().add(menu);
		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
	}

	@Override
	protected void initViewContent(final Request request) {
		List<T> beans = getViewBean();
		if (beans != null && !beans.isEmpty()) {
			setupMenu(beans);
		}
	}

	protected void setupMenu(final List<T> beans) {
		int i = 0;
		for (T bean : beans) {
			final WMenuItem item = new WMenuItem(bean.toString());
			item.setActionObject(i++);
			item.setAction(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					Integer idx = (Integer) event.getActionObject();
					handleSelected((WMenuItem) event.getSource(), idx);
				}
			});
			menu.add(item);
		}
	}

	protected void handleSelected(final WMenuItem item, final int idx) {
		menu.setSelectedMenuItem(item);
		setSelectedIdx(idx);
		T bean = getViewBean().get(idx);
		dispatchViewEvent(ActionEventType.LOAD, bean);
	}

}
