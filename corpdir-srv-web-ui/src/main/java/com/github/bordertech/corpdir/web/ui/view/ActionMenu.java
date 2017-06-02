package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.common.ActionMode;
import com.github.bordertech.corpdir.web.ui.common.RecordAction;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.validation.ValidatingAction;

/**
 * Action menu with AJAX controls.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ActionMenu extends WContainer {

	private final ActionView view;

	private final WMenu actionMenu = new WMenu();

	private final WMenuItem itemBack = new WMenuItem("Back") {
		@Override
		public boolean isVisible() {
			return view.getEventAction(RecordAction.Back) != null;
		}
	};

	private final WMenuItem itemEdit = new WMenuItem("Edit") {
		@Override
		public boolean isVisible() {
			return view.isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return view.getActionMode() != ActionMode.View;
		}
	};

	private final WMenuItem itemCancel = new WMenuItem("Cancel") {
		@Override
		public boolean isVisible() {
			return view.isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return view.getActionMode() != ActionMode.Edit;
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemRefresh = new WMenuItem("Refresh") {
		@Override
		public boolean isVisible() {
			return view.isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return view.getActionMode() == ActionMode.Create;
		}

	};

	private final WMenuItem itemSave = new WMenuItem("Save") {
		@Override
		public boolean isVisible() {
			return view.isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return view.getActionMode() == ActionMode.View;
		}
	};

	private final WMenuItem itemDelete = new WMenuItem("Delete") {
		@Override
		public boolean isVisible() {
			return view.isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return view.getActionMode() != ActionMode.View;
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	private final WAjaxControl ajaxBack = new MyAjaxControl(itemBack);
	private final WAjaxControl ajaxSave = new MyAjaxControl(itemSave);
	private final WAjaxControl ajaxEdit = new MyAjaxControl(itemEdit);
	private final WAjaxControl ajaxCancel = new MyAjaxControl(itemCancel);
	private final WAjaxControl ajaxDelete = new MyAjaxControl(itemDelete);
	private final WAjaxControl ajaxRefresh = new MyAjaxControl(itemRefresh);

	/**
	 * @param view the action view
	 *
	 */
	public ActionMenu(final ActionView view) {
		this.view = view;
		add(actionMenu);
		add(ajaxPanel);

		// Menu Items
		actionMenu.add(itemBack);
		actionMenu.add(itemEdit);
		actionMenu.add(itemSave);
		actionMenu.add(itemCancel);
		actionMenu.add(itemDelete);
		actionMenu.add(itemRefresh);

		// Actions
		itemBack.setAction(new MyAction(view, RecordAction.Back));
		itemCancel.setAction(new MyAction(view, RecordAction.Cancel));
		itemDelete.setAction(new MyAction(view, RecordAction.Delete));
		itemRefresh.setAction(new MyAction(view, RecordAction.Refresh));
		itemEdit.setAction(new MyValidatingAction(view, RecordAction.Edit));
		itemSave.setAction(new MyValidatingAction(view, RecordAction.Save));

		// AJAX
		ajaxPanel.add(ajaxBack);
		ajaxPanel.add(ajaxEdit);
		ajaxPanel.add(ajaxCancel);
		ajaxPanel.add(ajaxSave);
		ajaxPanel.add(ajaxDelete);
		ajaxPanel.add(ajaxRefresh);
	}

	public void addAjaxTarget(final AjaxTarget target) {
		ajaxBack.addTarget(target);
		ajaxSave.addTarget(target);
		ajaxDelete.addTarget(target);
		ajaxEdit.addTarget(target);
		ajaxCancel.addTarget(target);
		ajaxRefresh.addTarget(target);
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

	private static class MyAction implements Action {

		private final ActionView view;
		private final RecordAction action;

		public MyAction(final ActionView view, final RecordAction action) {
			this.view = view;
			this.action = action;
		}

		@Override
		public void execute(final ActionEvent event) {
			view.handleAction(action);
		}
	}

	private static class MyValidatingAction extends ValidatingAction {

		private final ActionView view;
		private final RecordAction action;

		public MyValidatingAction(final ActionView view, final RecordAction action) {
			super(view.getMessages().getValidationErrors(), view.getDetailView());
			this.view = view;
			this.action = action;
		}

		@Override
		public void executeOnValid(final ActionEvent event) {
			view.handleAction(action);
		}
	}

}
