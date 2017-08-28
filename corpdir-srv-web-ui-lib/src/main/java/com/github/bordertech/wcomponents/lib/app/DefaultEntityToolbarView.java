package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * Entity menu bar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEntityToolbarView extends DefaultToolbarView implements EntityToolbarView {

	private final WMenuItem itemEdit = new MyMenuItem("Edit", ActionEventType.EDIT) {
		@Override
		public boolean isVisible() {
			return isEntityLoaded() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			EntityMode mode = getEntityMode();
			return !EntityMode.VIEW.equals(mode);
		}
	};

	private final WMenuItem itemCancel = new MyMenuItem("Cancel", ActionEventType.CANCEL) {
		@Override
		public boolean isVisible() {
			return isEntityLoaded() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			EntityMode mode = getEntityMode();
			return EntityMode.VIEW.equals(mode);
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemUpdate = new MyMenuItem("Save", ActionEventType.UPDATE) {
		@Override
		public boolean isVisible() {
			return getEntityMode() == EntityMode.EDIT;
		}
	};

	private final WMenuItem itemCreate = new MyMenuItem("Save", ActionEventType.CREATE) {
		@Override
		public boolean isVisible() {
			return getEntityMode() == EntityMode.ADD;
		}
	};

	private final WMenuItem itemDelete = new MyMenuItem("Delete", ActionEventType.DELETE) {
		@Override
		public boolean isVisible() {
			return isEntityLoaded() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			EntityMode mode = getEntityMode();
			return EntityMode.ADD == mode || EntityMode.EDIT == mode;
		}
	};

	/**
	 * Construct the Menu Bar.
	 *
	 * @param dispatcher the controller for this view
	 */
	public DefaultEntityToolbarView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	/**
	 * Construct the Menu Bar.
	 *
	 * @param dispatcher the controller for this view
	 * @param qualifier the qualifier
	 */
	public DefaultEntityToolbarView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		WMenu menu = getMenu();
		menu.add(itemEdit);
		menu.add(itemUpdate);
		menu.add(itemCreate);
		menu.add(itemCancel);
		menu.add(itemDelete);

		setUseAdd(false);
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
	public boolean isEntityLoaded() {
		return getComponentModel().entityLoaded;
	}

	@Override
	public void setEntityLoaded(final boolean entityLoaded) {
		getOrCreateComponentModel().entityLoaded = entityLoaded;
	}

	@Override
	protected EntityToolbarModel newComponentModel() {
		return new EntityToolbarModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityToolbarModel getComponentModel() {
		return (EntityToolbarModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityToolbarModel getOrCreateComponentModel() {
		return (EntityToolbarModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class EntityToolbarModel extends ToolbarModel {

		private EntityMode entityMode = EntityMode.VIEW;

		private boolean entityLoaded;

	}

}
