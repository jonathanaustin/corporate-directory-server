package com.github.bordertech.wcomponents.lib.app.view.bar;

import com.github.bordertech.wcomponents.lib.app.common.AppMenuItem;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * Abstract Menu Toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractMenuToolbar<T> extends AbstractToolbar<T> {

	private final WMenu menu = new WMenu();

	private final WDiv ajaxPanel = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}

	};

	public AbstractMenuToolbar() {
		WContainer content = getContent();
		content.add(menu);
		content.add(ajaxPanel);
	}

	public final WMenu getMenu() {
		return menu;
	}

	public final WDiv getAjaxPanel() {
		return ajaxPanel;
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

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : menu.getMenuItems(true)) {
			if (menuItem instanceof WMenuItem) {
				WMenuItem item = (WMenuItem) menuItem;
				item.setAction(action);
				ajaxPanel.add(new AppAjaxControl(item, this));
			}
		}
	}

	protected void addTarget(final AjaxTarget target) {
		// Add a target to each AJAX control
		for (WComponent child : ajaxPanel.getChildren()) {
			WAjaxControl ctrl = (WAjaxControl) child;
			if (!ctrl.getTargets().contains(target)) {
				ctrl.addTarget(target);
			}
		}
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		addTarget(target);
	}

	protected void doDispatchToolbarEvent(final EventType eventType, final Object data) {
		dispatchEvent(eventType, data);
	}

}
