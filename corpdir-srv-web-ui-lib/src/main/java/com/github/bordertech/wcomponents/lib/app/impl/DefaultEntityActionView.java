package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.type.EntityActionType;
import com.github.bordertech.wcomponents.lib.app.view.EntityActionView;
import com.github.bordertech.wcomponents.lib.app.view.EntityMode;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import java.util.List;

/**
 * Action menu bar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEntityActionView extends DefaultView implements EntityActionView {

	private final WMenu actionMenu = new WMenu();

	private final WMenuItem itemBack = new MyMenuItem("Back", EntityActionType.BACK) {
		@Override
		public boolean isVisible() {
			return isUseBack();
		}
	};

	private final WMenuItem itemEdit = new MyMenuItem("Edit", EntityActionType.EDIT) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.VIEW.equals(getEntityMode());
		}
	};

	private final WMenuItem itemCancel = new MyMenuItem("Cancel", EntityActionType.CANCEL) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return !EntityMode.EDIT.equals(getEntityMode());
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemRefresh = new MyMenuItem("Refresh", EntityActionType.REFRESH) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.ADD.equals(getEntityMode());
		}

	};

	private final WMenuItem itemSave = new MyMenuItem("Save", EntityActionType.SAVE) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.VIEW.equals(getEntityMode());
		}
	};

	private final WMenuItem itemDelete = new MyMenuItem("Delete", EntityActionType.DELETE) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return !EntityMode.VIEW.equals(getEntityMode());
		}
	};

	private final WMenuItem itemAdd = new MyMenuItem("Add", EntityActionType.ADD);

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	/**
	 * Construct the Menu Bar.
	 *
	 * @param ctrl the controller for this view
	 */
	public DefaultEntityActionView(final BasicController ctrl) {
		super(ctrl);

		WDiv holder = getViewHolder();

		holder.add(actionMenu);
		holder.add(ajaxPanel);

		// Menu Items
		actionMenu.add(itemBack);
		actionMenu.add(itemEdit);
		actionMenu.add(itemSave);
		actionMenu.add(itemCancel);
		actionMenu.add(itemDelete);
		actionMenu.add(itemRefresh);
		actionMenu.add(itemAdd);

		// Action
		Action action = new Action() {
			@Override
			public void execute(final ActionEvent event) {
				MyMenuItem item = (MyMenuItem) event.getSource();
				dispatchViewEvent(item.getItemEvent());
			}
		};

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : actionMenu.getMenuItems()) {
			WMenuItem item = (WMenuItem) menuItem;
			item.setAction(action);
			ajaxPanel.add(new MyAjaxControl(item));
		}
	}

	protected void addTargets(final List<AjaxTarget> targets) {
		// Add a target to each AJAX control
		for (WComponent child : ajaxPanel.getChildren()) {
			WAjaxControl ctrl = (WAjaxControl) child;
			ctrl.addTargets(targets);
		}
	}

	@Override
	public void setEntityMode(final EntityMode mode) {
		getOrCreateComponentModel().entityMode = mode == null ? EntityMode.VIEW : mode;
	}

	@Override
	public EntityMode getEntityMode() {
		return getComponentModel().entityMode;
	}

	@Override
	public boolean isEntityReady() {
		return getComponentModel().entityReady;
	}

	@Override
	public void setEntityReady(final boolean entityReady) {
		getOrCreateComponentModel().entityReady = entityReady;
	}

	@Override
	public boolean isUseBack() {
		return getComponentModel().useBack;
	}

	@Override
	public void setUseBack(final boolean useBack) {
		getOrCreateComponentModel().useBack = useBack;
	}

	@Override
	public void doRefreshViewState() {
		// Do nothing
	}

	@Override
	protected EntityViewModel newComponentModel() {
		return new EntityViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityViewModel getComponentModel() {
		return (EntityViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityViewModel getOrCreateComponentModel() {
		return (EntityViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class EntityViewModel extends DefaultView.ViewModel {

		private EntityMode entityMode = EntityMode.VIEW;

		private boolean entityReady;

		private boolean useBack;
	}

	private static class MyMenuItem extends WMenuItem {

		private final EntityActionType event;

		public MyMenuItem(final String text, final EntityActionType event) {
			super(text);
			this.event = event;
		}

		public EntityActionType getItemEvent() {
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
}
