package com.github.bordertech.flux.wc.app.view.toolbar;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.flux.wc.app.common.AppMenuItem;

/**
 * Abstract Menu Toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractMenuToolbar<T> extends AbstractToolbar<T> {

	private final WMenu menu = new WMenu();

	public AbstractMenuToolbar(final String viewId) {
		super(viewId);
		WContainer content = getContent();
		content.add(menu);
	}

	public final WMenu getMenu() {
		return menu;
	}

	@Override
	protected void initViewContent(final Request request) {
		setupMenuAjax();
		super.initViewContent(request);
	}

	protected void setupMenuAjax() {
		// Action
		Action action = new Action() {
			@Override
			public void execute(final ActionEvent event) {
				AppMenuItem item = (AppMenuItem) event.getSource();
				doDispatchToolbarEvent(item.getItemEvent(), item.getItemData());
			}
		};

		// Add Action and AJAX control for each menu item and EVENT
		for (MenuItem menuItem : menu.getMenuItems(true)) {
			if (menuItem instanceof AppMenuItem) {
				AppMenuItem item = (AppMenuItem) menuItem;
				item.setAction(action);
				setupAjaxControl(item.getItemEvent(), item);
			}
		}
	}

	protected void doDispatchToolbarEvent(final ViewEventType eventType, final Object data) {
		dispatchViewEvent(eventType, data);
	}

}
