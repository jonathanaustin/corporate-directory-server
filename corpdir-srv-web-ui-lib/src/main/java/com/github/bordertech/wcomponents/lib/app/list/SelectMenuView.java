package com.github.bordertech.wcomponents.lib.app.list;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import java.util.List;
import java.util.Objects;

/**
 * Select menu view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SelectMenuView<T> extends DefaultSelectView<T> {

	private final WMenu menu = new WMenu(WMenu.MenuType.TREE) {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			findMenuItem();
		}
	};

	private final WDiv ajaxPanel = new WDiv();

	public SelectMenuView() {
		getContent().add(menu);
		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		getContent().add(ajaxPanel);
	}

	@Override
	protected void initViewContent(final Request request) {
		// Build menu before calling super (which adds AJAX)
		List<T> beans = getViewBean();
		if (beans != null && !beans.isEmpty()) {
			setupMenu(beans);
		}
		super.initViewContent(request);
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
			ajaxPanel.add(new WAjaxControl(item, this));
		}
	}

	protected void handleMenuItemSelected(final WMenuItem item, final int idx) {
		setSelectedIdx(idx);
		doDispatchSelectEvent();
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		for (WComponent ajax : ajaxPanel.getChildren()) {
			addEventTargetsToAjaxCtrl((WAjaxControl) ajax, target);
		}
	}

	protected void findMenuItem() {
		int idx = getSelectedIdx();
		if (idx > -1) {
			for (MenuItem item : menu.getMenuItems()) {
				if (item instanceof WMenuItem) {
					Integer menuIdx = (Integer) ((WMenuItem) item).getActionObject();
					if (Objects.equals(menuIdx, idx)) {
						menu.setSelectedMenuItem((WMenuItem) item);
						return;
					}
				}
			}
		}
		menu.setSelectedMenuItem(null);
	}

}
