package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.wc.common.AppMenuItem;
import com.github.bordertech.flux.wc.mode.FormMode;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;

/**
 * Entity form toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultFormToolbarView<T> extends DefaultToolbarView<T> implements FormToolbarView<T> {

	private final WMenuItem itemEdit = new AppMenuItem("Edit", ToolbarBaseEventType.EDIT) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.EDIT) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return !FormMode.VIEW.equals(mode);
		}
	};

	private final WMenuItem itemCancel = new AppMenuItem("Cancel", ToolbarBaseEventType.CANCEL) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.CANCEL) && isFormReady() && !isDisabled();
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

	private final WMenuItem itemUpdate = new AppMenuItem("Save", ToolbarBaseEventType.UPDATE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.UPDATE) && getFormMode() == FormMode.EDIT;
		}
	};

	private final WMenuItem itemCreate = new AppMenuItem("Save", ToolbarBaseEventType.CREATE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.CREATE) && getFormMode() == FormMode.ADD;
		}
	};

	private final WMenuItem itemDelete = new AppMenuItem("Delete", ToolbarBaseEventType.DELETE) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.DELETE) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return FormMode.ADD == mode || FormMode.EDIT == mode;
		}
	};

	private final WMenuItem itemRefresh = new AppMenuItem("Refresh", ToolbarBaseEventType.REFRESH) {
		@Override
		public boolean isVisible() {
			return isUseToolbarItem(ToolbarModifyItemType.REFRESH) && isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return !FormMode.VIEW.equals(mode);
		}
	};

	public DefaultFormToolbarView(final String viewId) {
		super(viewId);
		WMenu menu = getMenu();
		menu.add(itemEdit);
		menu.add(itemUpdate);
		menu.add(itemCreate);
		menu.add(itemCancel);
		menu.add(itemDelete);
		menu.add(itemRefresh);

		itemDelete.setMessage("Please confirm the delete action.");

		// Images
		ViewUtil.addImageToMenuItem(IconConstants.EDIT_IMAGE, itemEdit);
		ViewUtil.addImageToMenuItem(IconConstants.SAVE_IMAGE, itemUpdate);
		ViewUtil.addImageToMenuItem(IconConstants.SAVE_IMAGE, itemCreate);
		ViewUtil.addImageToMenuItem(IconConstants.CANCEL_IMAGE, itemCancel);
		ViewUtil.addImageToMenuItem(IconConstants.REMOVE_IMAGE, itemDelete);
		ViewUtil.addImageToMenuItem(IconConstants.REFRESH_IMAGE, itemRefresh);

		// Clear Defaults
		clearToolbarItems();
		addToolbarItem(ToolbarModifyItemType.EDIT);
		addToolbarItem(ToolbarModifyItemType.UPDATE);
		addToolbarItem(ToolbarModifyItemType.CREATE);
		addToolbarItem(ToolbarModifyItemType.CANCEL);
		addToolbarItem(ToolbarModifyItemType.DELETE);
		addToolbarItem(ToolbarModifyItemType.REFRESH);
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
