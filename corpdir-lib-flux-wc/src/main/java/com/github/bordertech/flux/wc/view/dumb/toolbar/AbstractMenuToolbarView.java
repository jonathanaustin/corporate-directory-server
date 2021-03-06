package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.FluxMenuItem;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;

/**
 * Abstract Menu Toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractMenuToolbarView<T> extends AbstractToolbarView<T> {

	private final WMenu menu = new WMenu();

	public AbstractMenuToolbarView(final String viewId) {
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
				FluxMenuItem item = (FluxMenuItem) event.getSource();
				doDispatchToolbarEvent(item.getItemEvent(), item.getItemData());
			}
		};

		// Add Action and AJAX control for each menu item and EVENT
		for (MenuItem menuItem : menu.getMenuItems(true)) {
			if (menuItem instanceof FluxMenuItem) {
				FluxMenuItem item = (FluxMenuItem) menuItem;
				item.setAction(action);
				setupAjaxControl(item.getItemEvent(), item);
			}
		}
	}

	protected void doDispatchToolbarEvent(final ViewEventType eventType, final Object data) {
		dispatchViewEvent(eventType, data);
	}

}
