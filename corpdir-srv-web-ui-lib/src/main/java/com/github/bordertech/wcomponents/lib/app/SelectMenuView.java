package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.List;

/**
 * Default list view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SelectMenuView<T> extends DefaultSelectView<T> {

	private final WMenu menu = new WMenu(WMenu.MenuType.TREE);

	public SelectMenuView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public SelectMenuView(final Dispatcher dispatcher, final String qualifier) {
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
					handleMenuItemSelected((WMenuItem) event.getSource(), idx);
				}
			});
			menu.add(item);
		}
	}

	protected void handleMenuItemSelected(final WMenuItem item, final int idx) {
		menu.setSelectedMenuItem(item);
		setSelectedIdx(idx);
		doDispatchSelectEvent();
	}

}
