package com.github.bordertech.wcomponents.lib.app.view;

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
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import java.util.List;

/**
 * Action menu bar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEntityCtrlView extends DefaultView implements EntityCtrlView {

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
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.VIEW.equals(getEntityMode());
		}
	};

	private final WMenuItem itemCancel = new MyMenuItem("Cancel", EntityCtrlEvent.CANCEL) {
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

	private final WMenuItem itemRefresh = new MyMenuItem("Refresh", EntityCtrlEvent.REFRESH) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.CREATE.equals(getEntityMode());
		}

	};

	private final WMenuItem itemSave = new MyMenuItem("Save", EntityCtrlEvent.SAVE) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return EntityMode.VIEW.equals(getEntityMode());
		}
	};

	private final WMenuItem itemDelete = new MyMenuItem("Delete", EntityCtrlEvent.DELETE) {
		@Override
		public boolean isVisible() {
			return isEntityReady();
		}

		@Override
		public boolean isDisabled() {
			return !EntityMode.VIEW.equals(getEntityMode());
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
	public DefaultEntityCtrlView(final Dispatcher dispatcher) {
		super(dispatcher);

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

		// Action
		Action action = new Action() {
			@Override
			public void execute(final ActionEvent event) {
				MyMenuItem item = (MyMenuItem) event.getSource();
				dispatchEvent(item.getItemEvent());
			}
		};

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : actionMenu.getMenuItems()) {
			WMenuItem item = (WMenuItem) menuItem;
			item.setAction(action);
			ajaxPanel.add(new MyAjaxControl(item));
		}
	}

	protected void dispatchEvent(final EventType eventType) {
		getDispatcher().dispatch(new Event(this, eventType));
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

		private final EntityCtrlEvent event;

		public MyMenuItem(final String text, final EntityCtrlEvent event) {
			super(text);
			this.event = event;
		}

		public EntityCtrlEvent getItemEvent() {
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
