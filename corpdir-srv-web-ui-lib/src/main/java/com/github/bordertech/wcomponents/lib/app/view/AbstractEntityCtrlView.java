package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.view.ViewEvent;

/**
 * Action menu bar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractEntityCtrlView extends WContainer implements EntityCtrlView {

	private final WMenu actionMenu = new WMenu();

	private final WMenuItem itemBack = new MyMenuItem("Back", EntityCtrlEvent.BACK) {
		@Override
		public boolean isVisible() {
			return isUseBack();
		}
	};

	private final WMenuItem itemEdit = new MyMenuItem("Edit", EntityCtrlEvent.EDIT) {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != EntityMode.View;
		}
	};

	private final WMenuItem itemCancel = new MyMenuItem("Cancel", EntityCtrlEvent.CANCEL) {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != EntityMode.Edit;
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemRefresh = new MyMenuItem("Refresh", EntityCtrlEvent.REFRESH) {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() == EntityMode.Create;
		}

	};

	private final WMenuItem itemSave = new MyMenuItem("Save", EntityCtrlEvent.SAVE) {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() == EntityMode.View;
		}
	};

	private final WMenuItem itemDelete = new MyMenuItem("Delete", EntityCtrlEvent.DELETE) {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != EntityMode.View;
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	/**
	 * Construct the Menu Bar.
	 */
	public AbstractEntityCtrlView() {

		add(actionMenu);
		add(ajaxPanel);

		// Menu Items
		actionMenu.add(itemBack);
		actionMenu.add(itemEdit);
		actionMenu.add(itemSave);
		actionMenu.add(itemCancel);
		actionMenu.add(itemDelete);
		actionMenu.add(itemRefresh);

		// Action
		Action action = new Action() {
			@Override
			public void execute(final ActionEvent event) {
				MyMenuItem item = (MyMenuItem) event.getSource();
				handleEvent(item.getRecordEvent());
			}
		};

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : actionMenu.getMenuItems()) {
			WMenuItem item = (WMenuItem) menuItem;
			item.setAction(action);
			ajaxPanel.add(new MyAjaxControl(item));
		}
	}

	@Override
	public void addAjaxTarget(final AjaxTarget target, final ViewEvent... viewEvent) {
		// Add a target to teach AJAX control
		for (WComponent child : ajaxPanel.getChildren()) {
			WAjaxControl ctrl = (WAjaxControl) child;
			ctrl.addTarget(target);
		}
	}

	private static class MyMenuItem extends WMenuItem {

		private final EntityCtrlEvent event;

		public MyMenuItem(final String text, final EntityCtrlEvent event) {
			super(text);
			this.event = event;
		}

		public EntityCtrlEvent getRecordEvent() {
			return event;
		}
	}

	private static class MyAjaxControl extends WAjaxControl {

		public MyAjaxControl(final AjaxTrigger trigger) {
			super(trigger);
		}

		@Override
		public boolean isVisible() {
			return getTrigger().isVisible();
		}
	}

	abstract boolean isUseBack();

	abstract boolean isLoaded();

	abstract void handleEvent(final EntityCtrlEvent event);

	abstract EntityMode getActionMode();

}
