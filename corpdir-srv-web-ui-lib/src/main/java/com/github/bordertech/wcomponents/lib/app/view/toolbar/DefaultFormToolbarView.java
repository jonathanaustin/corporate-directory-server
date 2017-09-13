package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.app.common.AppMenuItem;
import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;
import com.github.bordertech.wcomponents.lib.util.ViewUtil;

/**
 * Entity form toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultFormToolbarView<T> extends DefaultToolbarView<T> implements FormToolbarView<T> {

	private final WMenuItem itemEdit = new AppMenuItem("Edit", ModelEventType.EDIT) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelItem.EDIT) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return !FormMode.VIEW.equals(mode);
		}
	};

	private final WMenuItem itemCancel = new AppMenuItem("Cancel", ModelEventType.CANCEL) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelItem.CANCEL) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return FormMode.VIEW.equals(mode);
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemUpdate = new AppMenuItem("Save", ModelEventType.UPDATE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelItem.UPDATE) && getFormMode() == FormMode.EDIT;
		}
	};

	private final WMenuItem itemCreate = new AppMenuItem("Save", ModelEventType.CREATE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelItem.CREATE) && getFormMode() == FormMode.ADD;
		}
	};

	private final WMenuItem itemDelete = new AppMenuItem("Delete", ModelEventType.DELETE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModelItem.DELETE) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return FormMode.ADD == mode || FormMode.EDIT == mode;
		}
	};

	public DefaultFormToolbarView() {
		WMenu menu = getMenu();
		menu.add(itemEdit);
		menu.add(itemUpdate);
		menu.add(itemCreate);
		menu.add(itemCancel);
		menu.add(itemDelete);

		itemDelete.setMessage("Please confirm the delete action.");

		// Images
		ViewUtil.addImageToMenuItem(IconConstants.EDIT_IMAGE, itemEdit);
		ViewUtil.addImageToMenuItem(IconConstants.SAVE_IMAGE, itemUpdate);
		ViewUtil.addImageToMenuItem(IconConstants.SAVE_IMAGE, itemCreate);
		ViewUtil.addImageToMenuItem(IconConstants.CANCEL_IMAGE, itemCancel);
		ViewUtil.addImageToMenuItem(IconConstants.REMOVE_IMAGE, itemDelete);

		// Clear Defaults
		clearToolbarItems();
		addToolbarItem(ToolbarModelItem.EDIT);
		addToolbarItem(ToolbarModelItem.UPDATE);
		addToolbarItem(ToolbarModelItem.CREATE);
		addToolbarItem(ToolbarModelItem.CANCEL);
		addToolbarItem(ToolbarModelItem.DELETE);
	}

	@Override
	public void setFormMode(final FormMode mode) {
		getOrCreateComponentModel().entityMode = mode == null ? FormMode.VIEW : mode;
	}

	@Override
	public FormMode getFormMode() {
		return getComponentModel().entityMode;
	}

	@Override
	public boolean isFormReady() {
		return getComponentModel().entityLoaded;
	}

	@Override
	public void setFormReady(final boolean entityLoaded) {
		getOrCreateComponentModel().entityLoaded = entityLoaded;
	}

	@Override
	protected FormToolbarModel newComponentModel() {
		return new FormToolbarModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FormToolbarModel getComponentModel() {
		return (FormToolbarModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FormToolbarModel getOrCreateComponentModel() {
		return (FormToolbarModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormToolbarModel extends ToolbarModel {

		private FormMode entityMode = FormMode.VIEW;

		private boolean entityLoaded;

	}

}
